package com.itck.skill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itck.skill.config.SystemConfig;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 10:57
 */
@Data
@TableName("t_skillgoods")
public class Skillgoods {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 图片地址
     */
    private String picurl;
    /**
     * 描述信息
     */
    private String info;
    /**
     * 原价
     */
    private Double price;
    /**
     * 现价
     */
    private Double currprice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 商品类型
     */
    private Integer itemId;
    /**
     * 秒杀活动id
     */
    private Integer said;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 静态化页面地址
     */
    private String htmlurl;

    public Skillgoods() {
        this.ctime = new Date();
        this.flag = SystemConfig.GOODS_ADD;
    }

}
