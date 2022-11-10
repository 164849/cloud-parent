package org.itck.orders.config;

/**
 * 系统配置常量
 *
 * @author zed
 * @date 2022/11/10
 */
public interface SystemConfig {

    //商品状态 状态 101：未审核 102：审核成功 103：已上架 104：已下架 105：审核失败
    int GOODS_STATUS_ADD = 101;
    int GOODS_STATUS_SUCCESS = 102;
    int GOODS_STATUS_FAIL = 105;
    int GOODS_STATUS_UP = 103;
    int GOODS_STATUS_DOWN = 104;

    //订单超时的时间，时间单位是毫秒
    int ORDER_TIMEOUT = 60000;//1分钟

}
