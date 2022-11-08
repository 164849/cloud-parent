package com.itck.coupon.config;

// 系统字典
public interface SystemConfig {
    //自定义uid的消息头
    String HEADER_UID = "cp_uid";
    //优惠券的发送类型 81：用户优惠券 82：系统优惠券
    int COUPON_SEND_USER = 81;
    int COUPON_SEND_SYSTEM = 82;
    //优惠券作用的人群：71-全体；72-会员等级 73-新用户 74-收费会员
    int COUPON_TARGET_ALL = 71;
    int COUPON_TARGET_LEVEL = 72;
    int COUPON_TARGET_NEW = 73;
    int COUPON_TARGET_PLUS = 74;
    //批量新增用户优惠券数据的数量  线程池的任务处理的数量
    int THREAD_COUPON_BATCH = 1000;

    //优惠券的使用状态 91-未使用 92-已使用 93-无效
    int USER_COUPON_NO = 91;
    int USER_COUPON_USED = 92;
    int USER_COUPON_DEAD = 93;
}
