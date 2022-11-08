package com.itck.coupon.entity;

import com.itck.coupon.config.SystemConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 9.用户优惠券表(TUsercoupon)实体类
 *
 * @author makejava
 * @since 2022-09-26 14:47:20
 */
@Data
public class TUsercoupon implements Serializable {
    private static final long serialVersionUID = -71212782766340228L;

    private Integer id;
    /**
     * 优惠券模板ID
     */
    private Integer templateId;
    /**
     * 前端用户ID
     */
    private Integer userId;
    /**
     * 优惠券码
     */
    private String couponCode;
    /**
     * 优惠券分发时间
     */
    private Date assignDate;
    /**
     * 优惠券状态
     */
    private Integer status;

    // 在构造方法中 把固定的属性直接赋值
    public TUsercoupon(Integer templateId, Integer userId, String couponCode) {
        this.templateId = templateId;
        this.userId = userId;
        this.couponCode = couponCode;
        this.assignDate = new Date();
        this.status = SystemConfig.USER_COUPON_NO;
    }

}
