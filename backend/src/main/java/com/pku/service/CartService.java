package com.pku.service;


import com.pku.pojo.entity.Cart;

public interface CartService {
    // 添加商品到购物车
    void addToCart(String userId, Long productId, Integer quantity);
    // 从购物车中删除商品
    void removeFromCart(String userId, Long productId);
    // 查询购物车详情
    Cart getCart(String userId);
    // 修改购物车中商品的数量
    void updateQuantity(String userId, Long productId, Integer newQuantity);
}