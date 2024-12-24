package com.pku.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private Long goodsId;
    private Integer goodsCount;
}
