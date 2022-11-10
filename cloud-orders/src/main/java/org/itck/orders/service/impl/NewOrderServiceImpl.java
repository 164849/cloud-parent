package org.itck.orders.service.impl;

import com.itck.entity.ddd.Goods;
import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsDto;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.entity.dto.OrderAddDto;
import com.itck.entity.dto.OrderFlagDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.api.GoodsProvider;
import org.itck.orders.config.OrderFlag;
import org.itck.orders.config.SystemConfig;
import org.itck.orders.entity.TOrder;
import org.itck.orders.entity.TOrderitem;
import org.itck.orders.entity.TOrderlog;
import org.itck.orders.mapper.OrderItemMapper;
import org.itck.orders.mapper.OrderMapper;
import org.itck.orders.mapper.TOrderlogMapper;
import org.itck.orders.service.NewOrderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
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

    @Override
    public R save(OrderAddDto dto, int uid) {
        R<Goods> response = goodsProvider.single(dto.getGid());
        if (response.getCode() == 200) {
            Goods goods = response.getData();
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
        return null;
    }

    @Override
    public R save2(OrderAddDto dto, int uid) {
        return null;
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
