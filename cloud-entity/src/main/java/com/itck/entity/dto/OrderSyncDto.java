package com.itck.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSyncDto implements Serializable {
    private String no;//订单编号
    private int ucid;//优惠券
    private int gid;// 商品ID
    private int num;// 商品数量
    private double price;// 商品价格
}
