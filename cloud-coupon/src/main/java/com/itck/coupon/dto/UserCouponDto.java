package com.itck.coupon.dto;

import lombok.Data;

import java.util.Date;

// 用户优惠券对象
// 数据传输对象 DTO
// data transfer object
@Data
public class UserCouponDto {
    private Integer id;//用户优惠券id
    private Integer tid;//用户优惠券模板id
    private Integer uid;//用户id
    private String couponCode;//用户优惠券券码
    private Integer status;//用户优惠券id
    private Date startTime;//优惠券生效起始时间
    private Date endTime;//优惠券生效结束时间
    private double limitmoney;//要求，满减
    private double discount;//减免的金额或者打折
    private Integer category;//优惠券减免额类型：种类: 51-满减；52-折扣；53-立减
    private String name;//优惠券名称
    private String logo;//优惠券显示的logo

}
