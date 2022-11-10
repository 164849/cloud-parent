package com.itck.entity.dto;

import lombok.Data;

@Data
public class OrderAddDto {
    private Integer gid;//商品id
    private Integer uaid;//用户收货地址
    private Integer num;//购买数量
    private Integer score;//抵扣积分
    private int ucid;//优惠券id
}