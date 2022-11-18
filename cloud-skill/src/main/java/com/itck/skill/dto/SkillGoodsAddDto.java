package com.itck.skill.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: zed
 * @Date: 2022/5/20 11:11
 */
@Data
public class SkillGoodsAddDto {
    /**
     * 秒杀商品的名称
     */
    private String title;
    /**
     * 商品图片地址
     */
    private String picurl;
    /**
     * 商品描述信息
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
     * 商品类型
     */
    private Integer itemId;
    /**
     * 秒杀活动
     */
    private Integer said;
}
