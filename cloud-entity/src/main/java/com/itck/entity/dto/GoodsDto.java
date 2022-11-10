package com.itck.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    private Integer id;
    private Integer itemId;
    private Integer status;
    private double price;
    private Integer stockNum;
}