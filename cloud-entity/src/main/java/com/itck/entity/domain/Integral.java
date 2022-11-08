package com.itck.entity.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Integral {
    private Integer id;

    private String orderId;
    private Integer integral;
}
