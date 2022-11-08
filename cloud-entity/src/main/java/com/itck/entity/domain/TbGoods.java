package com.itck.entity.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbGoods implements Serializable {

    private static final long serialVersionUID = -7165431574096870320L;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 商品库存数量
     */
    private Integer goodsStock;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 商品名称
     */
    private String goodsName;
}