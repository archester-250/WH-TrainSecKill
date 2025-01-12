package com.pku.pojo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private String userId;  // 假设关联用户ID来区分不同用户的购物车
    private List<CartItem> cartItems;

    // 构造函数、Getter和Setter方法等省略
}