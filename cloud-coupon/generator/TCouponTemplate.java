package generator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_coupon_template
 * @author 
 */
@Data
public class TCouponTemplate implements Serializable {
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
    private Date expireTime;

    /**
     * 优惠券发放数量
     */
    private Integer couponCount;

    /**
     * 创建时间
     */
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
    private Date startTime;

    /**
     * 优惠券失效日期
     */
    private Date endTime;

    /**
     * 优惠券可以使用的金额，满减、满折等
     */
    private BigDecimal limitmoney;

    /**
     * 减免或折扣
     */
    private Double discount;

    private static final long serialVersionUID = 1L;
}