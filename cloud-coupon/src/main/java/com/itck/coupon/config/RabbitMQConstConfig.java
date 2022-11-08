package com.itck.coupon.config;

public interface RabbitMQConstConfig {
    //队列名
    String Q_USERINIT = "cp-userinit";
    String Q_USERSCORE = "cp-userscore";

    String Q_COUPONSYS = "cp-couponsys";
    String Q_COUPONUSE = "cp-couponuse";

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

    //路由关键字
    String RK_USERADD = "rk-useradd";
    // 路由key
    String RK_COUPONSYS = "rk-coupon-sys";
    String RK_COUPONUSE = "rk-coupon-use";

    String RK_DEAD_ORDERTO = "rk-order-timeout";
    String RK_DEAD_SKILLORDERTO = "rk-skillorder-timeout";

    //MQ消息类型
    int MQTYPE_USERADD = 1;
    int MQTYPE_USERSIGN = 2;
    int MQTYPE_USERLOFIN = 3;
    int MQTYPE_COUPONSYS = 4;//优惠券模板 系统发放
    int MQTYPE_COUPONUSE = 5;//优惠券模板 用户领取
    int MQTYPE_ORDERADD = 6;//下单
    int MQTYPE_SKILLORDERADD = 8;//秒杀下单
    int MQTYPE_ORDERSYNC = 7;//订单同步
    int MQTYPE_ORDERCOMMENT = 9;//订单评价

}
