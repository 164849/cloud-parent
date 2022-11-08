package com.itck.coupon.config;

/**
 * 优惠券模板审核状态
 * 枚举类 不需要set方法 枚举的几个值是固定
 * 需要通过构造方法 设置枚举的几个值
 */
public enum CouponAudit {
    未审核(41), 审核通过(42), 审核拒绝(43);

    CouponAudit(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }
}
