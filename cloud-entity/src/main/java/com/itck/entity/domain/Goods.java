package com.itck.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 商品实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    private Integer id;
    private String name;
    private Integer price;
}