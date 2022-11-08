package com.itck.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 8.优惠券模板表(TCouponTemplate)实体类
 *
 * @author makejava
 * @since 2022-09-26 14:46:36
 */
@Data
public class TCouponTemplate implements Serializable {
    private static final long serialVersionUID = 778554933659508581L;

    private Integer id;
    /**
     * 状态：41.未审核 42.审核通过 43.审核失败
     */
    private Integer flag;
    /**
     * 名字
     */
    private String name;

    private String logo;
    /**
     * 简介
     */
    private String intro;
    /**
     * 种类: 51-满减；52-折扣；53-立减
     */
    private Integer category;
    /**
     * 使用范围：61-单品；62-商品类型；63-全品
     */
    private Integer scope;
    /**
     * 对应的id：单品id；商品类型id；全品为0
     */
    private Integer scopeId;
    /**
     * 优惠券发放结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    /**
     * 优惠券发放数量
     */
    private Integer couponCount;
    /**
     * 创建时间
     */
    // 解析参数的，前端传递的是string类型的字串时 string->date
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 实体对象转换为json字串的互转 json <-> date
    // 前端传参如果是json字串 就按照JsonFormat进行反序列化
    // 后端如果需要返回json字串 就按照JsonFormat进行序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人的ID，后台内部员工
     */
    private Integer userId;
    /**
     * 审核意见
     */
    private String userAudit;
    /**
     * 优惠券模板的识别码(有一定的识别度)
     */
    private String templateKey;
    /**
     * 优惠券作用的人群：71-全体；72-会员等级 73-新用户 74-收费会员
     */
    private Integer target;
    /**
     * 用户等级要求，默认0
     */
    private Integer targetLevel;
    /**
     * 发放类型：81.用户领取 82.系统发放
     */
    private Integer sendType;
    /**
     * 优惠券生效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 优惠券失效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 优惠券可以使用的金额，满减、满折等
     */
    private Double limitmoney;
    /**
     * 减免或折扣
     */
    private Double discount;

}
