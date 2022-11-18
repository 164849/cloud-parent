package com.itck.skill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itck.skill.config.SystemConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 10:59
 */
@Data
@TableName("t_skillorder")
@NoArgsConstructor
public class Skillorder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    private String no;
    /**
     * 商品id
     */
    private Integer sgid;
    /**
     * 价格
     */
    private Double price;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 收货地址
     */
    private Integer uaid;
    /**
     * 用户id
     */
    private Integer uid;

    public Skillorder(String no, Integer sgid, Double price, Integer num, Integer uaid, Integer uid) {
        this.no = no;
        this.sgid = sgid;
        this.price = price;
        this.num = num;
        this.uaid = uaid;
        this.uid = uid;
        this.ctime = new Date();
        this.utime = this.ctime;
        this.flag = SystemConfig.ORDER_ADD;
    }
}
