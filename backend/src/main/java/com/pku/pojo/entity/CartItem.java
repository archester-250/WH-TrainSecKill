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
public class CartItem{
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    // 构造函数、Getter和Setter方法等省略
}