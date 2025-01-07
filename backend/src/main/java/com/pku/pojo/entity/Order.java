package com.pku.pojo.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * 订单状态 1待付款 2代发货 3正在运送 4取消
     */
    public static final Integer WAITING_PAYING = 1;
    public static final Integer TO_BE_DELETED = 2;
    public static final Integer BEING_DELIVERED = 3;
    public static final Integer CANCELLED = 4;

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal goodsPrice;
    private int orderChannel;
    private int status;
    private LocalDateTime createDate;
    private LocalDateTime payDate;
}
