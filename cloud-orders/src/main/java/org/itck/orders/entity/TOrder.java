package org.itck.orders.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 13.订单表(TOrder)实体类
 *
 * @author zed
 * @since 2022-11-10 16:32:46
 */
public class TOrder implements Serializable {
    private static final long serialVersionUID = -49745167262167471L;

    private Integer id;

    private Integer uid;
    /**
     * 用户收货地址
     */
    private Integer uaid;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 支付金额
     */
    private Double payMoney;
    /**
     * 优惠金额
     */
    private Double freeMoney;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 订单状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 订单编号
     */
    private String no;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUaid() {
        return uaid;
    }

    public void setUaid(Integer uaid) {
        this.uaid = uaid;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(Double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

}

