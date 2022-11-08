package com.itck.coupon.config;

// 记录所有的key
public interface RedisKeyConfig {
    //存储优惠券信息
    //追加优惠券模板id,值存储数量，有效期为优惠券模板的领取的结束时间
    //public static final String COUPON_CACHE="cp:soupon:";//追加:模板id:等级id
    String COUPON_CACHE = "cp:coupon:";//追加:模板id List类型，第一个元素：数量 第二个元素：等级要求
    //存储用户领过的优惠券，用来解决 用户领取优惠券的限领的问题,有效期：优惠券模板的领取的时间
    String COUPON_USERS = "cp:coupon:users:";//追加模板id,Set类型 值记录uid,有效期

    //设置分布式锁的key,优惠券的领取 防止超领
    String COUPON_LOCK = "cp:coupon:rl:";//追加模板id
    int COUPON_LOCK_TIME = 10;//10秒
}
