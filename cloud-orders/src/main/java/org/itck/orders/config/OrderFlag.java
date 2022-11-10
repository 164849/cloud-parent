package org.itck.orders.config;

/**
 * 订单状态枚举类
 *
 * @author zed
 * @date 2022/11/10
 */
public enum OrderFlag {
    待付款(111), 待发货(112), 已发货(113), 已完成(114), 已关闭(115), 已超时(116), 已确认(117), 已评价(118);
    private int code;

    private OrderFlag(int c) {
        code = c;
    }

    public int getCode() {
        return code;
    }
}
