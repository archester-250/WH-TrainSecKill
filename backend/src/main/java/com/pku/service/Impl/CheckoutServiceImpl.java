package com.pku.service.Impl;

import com.pku.pojo.entity.Cart;
import com.pku.pojo.entity.CartItem;
import com.pku.service.CartService;
import com.pku.service.CheckoutService;

import java.math.BigDecimal;

import com.pku.service.MQGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CartService cartService;
    @Autowired
    private MQGoodsService mqGoodsService;

    @Override
    public BigDecimal checkout(String userId) {
        Cart cart = cartService.getCart(userId);
        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            Long productId = item.getProductId();
            Integer quantity = item.getQuantity();
            // 扣减库存
            mqGoodsService.decrByStock(productId, quantity);
            // 计算总价
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        // 结算后可以清空购物车等操作，此处省略具体代码
        return totalPrice;
    }
}
