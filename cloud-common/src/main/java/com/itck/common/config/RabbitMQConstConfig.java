package com.itck.common.config;

/**
 * 兔子MQ常量配置
 *
 * @author zed
 * @date 2022/11/11
 */
public interface RabbitMQConstConfig {

    String Q_ORDER_SYNC = "cp-ordersync";//订单同步：redis-mysql
    String Q_ORDER_DEAD = "cp-orderdead";//订单死信消息
    String Q_ORDER_TIMEOUT = "cp-ordertimeout";//订单延迟

    String Q_SKILLORDER_SYNC = "cp-skillordersync";//秒杀订单同步：redis-mysql
    String Q_SKILLORDER_DEAD = "cp-skillorderdead";//秒杀订单死信消息
    String Q_SKILLORDER_TIMEOUT = "cp-skillordertimeout";//秒杀订单延迟

    //交换器名
    String EX_USERADD = "ex-d-useradd";
    String EX_COUPONTEM = "ex-d-coupontem";

    String EX_ORDERADD = "ex-f-orderadd";//转发下单数据
    String EX_DEAD = "ex-d-dead";//死信交换器

    String EX_SKILLORDERADD = "ex-f-skillorderadd";//转发秒杀下单数据

    String RK_DEAD_ORDERTO = "rk-order-timeout";
    String RK_DEAD_SKILLORDERTO = "rk-skillorder-timeout";

    int MQTYPE_ORDERADD = 6;//下单
    int MQTYPE_SKILLORDERADD = 8;//秒杀下单
    int MQTYPE_ORDERSYNC = 7;//订单同步
    int MQTYPE_ORDERCOMMENT = 9;//订单评价

}