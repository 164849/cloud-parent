package org.itck.orders.service.impl;

import com.itck.common.utils.RedissonUtils;
import com.itck.common.utils.SnowFlowUtil;
import com.itck.entity.bo.MqMsgBo;
import com.itck.entity.ddd.Goods;
import com.itck.entity.domain.R;
import com.itck.entity.dto.*;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.api.GoodsProvider;
import org.itck.orders.config.OrderFlag;
import org.itck.orders.config.RabbitMQConstConfig;
import org.itck.orders.config.RedisKeyConfig;
import org.itck.orders.config.SystemConfig;
import org.itck.orders.entity.TOrder;
import org.itck.orders.entity.TOrderitem;
import org.itck.orders.entity.TOrderlog;
import org.itck.orders.mapper.OrderItemMapper;
import org.itck.orders.mapper.OrderMapper;
import org.itck.orders.mapper.TOrderlogMapper;
import org.itck.orders.service.NewOrderService;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewOrderServiceImpl implements NewOrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final TOrderlogMapper orderLogMapper;

    private final GoodsProvider goodsProvider;

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    @GlobalTransactional
    public R save(OrderAddDto dto, int uid) {
        R<GoodsDto> response = goodsProvider.single(dto.getGid());
        if (response.getCode() == 200) {
            GoodsDto goods = response.getData();
            if (Objects.nonNull(goods) && goods.getStatus() == SystemConfig.GOODS_STATUS_UP) {
                Lock lock = new ReentrantLock();
                try {
                    lock.lock();
                    Integer num = goods.getStockNum();
                    if (num >= dto.getNum()) {
                        //下单操作
                        TOrder order = new TOrder();
                        // 补全订单信息
                        order.setFlag(OrderFlag.待付款.getCode());
                        order.setCreateTime(new Date());
                        order.setUpdateTime(new Date());
                        order.setNo(UUID.randomUUID().toString().replace("-", ""));
                        // 订单的总金额是 商品价格*购买数量
                        order.setTotalMoney(goods.getPrice() * dto.getNum());
                        // 优惠金额 积分数量/100 100个积分抵扣一元
                        order.setFreeMoney(dto.getScore() / 100D);
                        order.setUaid(dto.getUaid());
                        order.setUid(uid);
                        if (orderMapper.save(order) > 0) {
                            GoodsStockDto stockDto = new GoodsStockDto();
                            stockDto.setId(goods.getId());
                            stockDto.setNum(-dto.getNum());
                            R r = goodsProvider.update(stockDto);
                            if (r.getCode() == 200) {
                                // 库存更新成功
                                // 4.2 订单详情表新增一条数据
                                TOrderitem item = new TOrderitem(order.getId(), dto.getGid(), goods.getPrice(), dto.getNum());
                                orderItemMapper.insert(item);
                                // 4.4 订单记录表中新增一条数据
                                orderLogMapper.insert(new TOrderlog(order.getId(), OrderFlag.待付款.getCode(), "商品详情下单成功！"));
                                return R.ok("商品下单成功！");
                            } else {
                                return R.fail("商品库存更新失败");
                            }

                        }

                    } else {
                        return R.fail("库存不够");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                return R.fail("商品已经下架");
            }
        }
        return R.ok();
    }

    @Override
    public R save2(OrderAddDto dto, int uid) {
        log.info("{}下单操作,商品ID是{}", uid, dto.getGid());
        // 远程调用商品服务获取道商品对象
        R<GoodsDto> response = goodsProvider.single(dto.getGid());
        if (response.getCode() == 200) {
            GoodsDto goods = response.getData();
            if (Objects.nonNull(goods) && goods.getStatus() == SystemConfig.GOODS_STATUS_UP) {
                // 商品存在并且状态是上架状态，可以下单 下单之前添加锁，防止超卖
                RLock lock = RedissonUtils.getLock(RedisKeyConfig.ORDER_ADD + dto.getGid());
                try {
                    if (lock.tryLock(10, TimeUnit.SECONDS)) {
                        // 判断库存是否充足
                        Integer stockNum = goods.getStockNum();
                        if (stockNum >= dto.getNum()) {
                            // 下单操作
                            // 4.1 订单表新增一条数据 insert
                            TOrder order = new TOrder();
                            // 补全订单信息
                            order.setFlag(OrderFlag.待付款.getCode());
                            order.setCreateTime(new Date());
                            order.setUpdateTime(new Date());
                            order.setNo(UUID.randomUUID().toString().replace("-", ""));
                            // 订单的总金额是 商品价格*购买数量
                            order.setTotalMoney(goods.getPrice() * dto.getNum());
                            // 优惠金额 积分数量/100 100个积分抵扣一元
                            order.setFreeMoney(dto.getScore() / 100D);
                            order.setUaid(dto.getUaid());
                            order.setUid(uid);
                            // 把订单对象放入redis中
                            RedissonUtils.setHash(RedisKeyConfig.ORDER_ADD, order.getNo(), order);
                            // 把订单对象发送到MQ中，让MQ的消费者异步操作数据库
                            // MQ发送消息给订单的交换机
                            OrderSyncDto orderSyncDto = new OrderSyncDto();
                            orderSyncDto.setGid(dto.getGid());
                            orderSyncDto.setNum(dto.getNum());
                            orderSyncDto.setPrice(goods.getPrice());
                            orderSyncDto.setUcid(dto.getUcid());
                            orderSyncDto.setNo(order.getNo());
                            // 到MQ的交换机发送订单对象
                            rabbitTemplate.convertAndSend(RabbitMQConstConfig.EX_ORDERADD, "",
                                    new MqMsgBo(SnowFlowUtil.getInstance().nextId(), RabbitMQConstConfig.MQTYPE_ORDERSYNC, orderSyncDto));

                            /*
                            // 让MQ的消费者异步操作数据库
                            if (orderMapper.save(order) > 0) {
                                // 新增订单成功
                                // 4.3 更新商品库存 [远程调用]
                                GoodsStockDto stockDto = new GoodsStockDto();
                                // 商品ID
                                stockDto.setId(goods.getId());
                                // 下单数量,如果是负值就是减库存，如果是正数那么就是加库存
                                stockDto.setNum(-dto.getNum());
                                R r = goodsProvider.update(stockDto);
                                if (r.getCode() == 200) {
                                    // 库存更新成功
                                    // 4.2 订单详情表新增一条数据
                                    TOrderitem item = new TOrderitem(order.getId(), dto.getGid(), goods.getPrice(), dto.getNum());
                                    orderItemMapper.insert(item);
                                    // 4.4 订单记录表中新增一条数据
                                    orderLogMapper.insert(new TOrderlog(order.getId(), OrderFlag.待付款.getCode(), "商品详情下单成功！"));
                                    return R.ok("商品下单成功！");
                                } else {
                                    return R.fail("商品库存更新失败");
                                }
                            }*/

                        } else {
                            return R.fail("抱歉，此商品库存不足");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            } else {
                return R.fail("您要买的商品不存在");
            }
        }
        return R.fail("商品服务开小差了");

    }

    @Override
    public R queryMy(int uid, int flag) {
        return null;
    }

    @Override
    public R updateFlag(OrderFlagDto dto) {
        return null;
    }

    @Override
    public R cancel(String no, int uid) {
        return null;
    }
}
