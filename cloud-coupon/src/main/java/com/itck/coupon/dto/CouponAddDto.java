package com.itck.coupon.dto;

import lombok.Data;

import java.util.Date;

// 优惠券模板对象
@Data
public class CouponAddDto {
    private String name;
    private String logo;
    private String intro;
    private Integer category;
    private Integer scope;
    private Integer scopeId;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    private Integer couponCount;
    private Integer target;
    private Integer targetLevel;
    private Integer sendType;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private double limitmoney;
    private double discount;
}
