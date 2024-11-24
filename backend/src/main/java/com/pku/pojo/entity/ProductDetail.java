package com.pku.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    private Long id;
    private String name;
    private String title;
    private String img;
    private String detail;
    private BigDecimal price;
    private Integer stock;
}
