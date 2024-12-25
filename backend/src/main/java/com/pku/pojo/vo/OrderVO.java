package com.pku.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderVO {
    public Long id;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal goodsPrice;
    private int status;
    private LocalDateTime createDate;
    private LocalDateTime payDate;
}
