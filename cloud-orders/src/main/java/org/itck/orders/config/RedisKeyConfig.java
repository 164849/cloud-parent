package org.itck.orders.config;

/**
 * Redis的key常量配置
 *
 * @author zed
 * @date 2022/11/11
 */
public interface RedisKeyConfig {
    //Redis 存储新订单
    String ORDER_ADD = "cp:orders";//Hash类型 存储所有的新订单 没有有效期
}
