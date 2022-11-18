package org.itck.orders.listener;

import com.itck.common.config.RabbitMQConstConfig;
import com.itck.entity.bo.MqMsgBo;
import com.itck.entity.dto.GoodsStockDto;
import com.itck.entity.dto.OrderDetailDto;
import com.itck.entity.dto.OrderSyncDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itck.orders.api.GoodsProvider;
import org.itck.orders.config.OrderFlag;
import org.itck.orders.entity.TOrder;
import org.itck.orders.entity.TOrderlog;
import org.itck.orders.mapper.OrderItemMapper;
import org.itck.orders.mapper.OrderMapper;
import org.itck.orders.mapper.TOrderlogMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 订单超时侦听器[消费者]
 *
 * @author zed
 * @date 2022/11/11
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_ORDER_TIMEOUT)
public class OrderTimeoutListener {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final TOrderlogMapper orderLogMapper;

    private final GoodsProvider goodsProvider;


    /**
     * 取消订单处理程序
     * 1、判断订单状态是否是待付款 是
     * 2、更新订单状态 【已超时】
     * 3、释放库存 商品下单数量再加回去
     * 4、新增订单日志记录
     *
     * @param bo 薄
     */
    @RabbitHandler
    public void handler(MqMsgBo bo) {
        if (bo.getType() == RabbitMQConstConfig.MQTYPE_ORDERSYNC) {
            OrderSyncDto dto = (OrderSyncDto) bo.getData();
            if (Objects.nonNull(dto)) {
                // 订单编号
                String no = dto.getNo();
                // 根据订单编号查询订单对象
                TOrder order = orderMapper.findByNo(no);
                // 判断订单状态
                if (order.getFlag() == OrderFlag.待付款.getCode()) {
                    // 如果超时了 并且还是未付款状态，那就取消订单
                    if (orderMapper.updateFlag(order.getId(), OrderFlag.已超时.getCode()) > 0) {
                        // 释放库存，查询所有的订单项
                        List<OrderDetailDto> orderDetailList = orderItemMapper.selectByOid(order.getId());
                        orderDetailList.forEach(od -> goodsProvider.update(new GoodsStockDto(od.getGid(), od.getNum())));
                        // 新增订单日志记录
                        orderLogMapper.insert(new TOrderlog(order.getId(), OrderFlag.已超时.getCode(), "订单超时取消"));
                        log.info("订单{}超时了，取消成功", order.getId());
                    } else {
                        log.info("订单{}超时了，取消失败", order.getId());
                    }
                } else {
                    log.info("订单{}超时了，取消失败", order.getId());
                }
            }
        }
    }
}
