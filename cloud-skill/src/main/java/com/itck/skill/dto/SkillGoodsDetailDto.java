package com.itck.skill.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zed
 * @Date: 2022/5/20 11:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillGoodsDetailDto implements Serializable {
    private Integer said;//活动ID
    private Integer sgid;//商品ID
    private String title;//活动名称
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date etime;
    /**
     * 最大购买量
     */
    private Integer maxcount;
    /**
     * 商品名称
     */
    private String gtitle;
    /**
     * 商品图片地址
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
}
