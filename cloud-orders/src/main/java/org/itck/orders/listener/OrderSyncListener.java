package org.itck.orders.listener;

import com.itck.common.config.RabbitMQConstConfig;
import com.itck.common.utils.RedissonUtils;
import com.itck.entity.bo.MqMsgBo;
import com.itck.entity.domain.R;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.entity.dto.OrderSyncDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.api.GoodsProvider;
import org.itck.orders.config.OrderFlag;
import org.itck.orders.config.RedisKeyConfig;
import org.itck.orders.entity.TOrder;
import org.itck.orders.entity.TOrderitem;
import org.itck.orders.entity.TOrderlog;
import org.itck.orders.mapper.OrderItemMapper;
import org.itck.orders.mapper.OrderMapper;
import org.itck.orders.mapper.TOrderlogMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 订单同步监听器[消费者]
 *
 * @author zed
 * @date 2022/11/11
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_ORDER_SYNC)
public class OrderSyncListener {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final TOrderlogMapper orderLogMapper;
    private final GoodsProvider goodsProvider;

    @RabbitHandler
    public void handler(MqMsgBo bo) {
        if (bo.getType() == RabbitMQConstConfig.MQTYPE_ORDERSYNC) {
            // 如果是同步订单的消息
            OrderSyncDto dto = (OrderSyncDto) bo.getData();
            if (Objects.nonNull(dto)) {
                // 订单的编号，从redis中取出来订单对象
                TOrder order = (TOrder) RedissonUtils.getHash(RedisKeyConfig.ORDER_ADD, dto.getNo());
                // 让MQ的消费者异步操作数据库
                if (orderMapper.save(order) > 0) {
                    // 新增订单成功
                    // 4.3 更新商品库存 [远程调用]
                    GoodsStockDto stockDto = new GoodsStockDto();
                    // 商品ID
                    stockDto.setId(dto.getGid());
                    // 下单数量,如果是负值就是减库存，如果是正数那么就是加库存
                    stockDto.setNum(-dto.getNum());
                    R r = goodsProvider.update(stockDto);
                    if (r.getCode() == 200) {
                        // 库存更新成功
                        // 4.2 订单详情表新增一条数据
                        TOrderitem item = new TOrderitem(order.getId(), dto.getGid(), dto.getPrice(), dto.getNum());
                        orderItemMapper.insert(item);
                        // 4.4 订单记录表中新增一条数据
                        orderLogMapper.insert(new TOrderlog(order.getId(), OrderFlag.待付款.getCode(), "商品详情下单成功！"));
                        log.info("商品下单成功！");
                    } else {
                        log.info("商品库存更新失败");
                    }
                }
            }
        }
    }
}
