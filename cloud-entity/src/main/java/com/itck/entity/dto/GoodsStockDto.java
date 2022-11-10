package com.itck.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsStockDto {
    private Integer id;//商品id
    private int num;//修改的库存数量
}