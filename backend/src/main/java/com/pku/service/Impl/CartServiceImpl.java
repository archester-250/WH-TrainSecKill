package com.pku.service.Impl;

import com.pku.pojo.entity.Cart;
import com.pku.pojo.entity.CartItem;
import com.pku.pojo.entity.Product;
import com.pku.service.CartService;

import com.pku.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_PREFIX = "cart:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(String userId, Long productId, Integer quantity) {
        String cartKey = CART_PREFIX + userId;
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCartItems(new ArrayList<>());
        }
        List<CartItem> cartItems = cart.getCartItems();
        // 查找购物车中是否已存在该商品，若存在则更新数量，否则添加新的购物车项
        Optional<CartItem> existingItemOptional = cartItems.stream()
                .filter(item -> Objects.equals(item.getProductId(), productId))
                .findFirst();
        if (existingItemOptional.isPresent()) {
            CartItem existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            Product product = productService.findProductById(productId);
            // 假设可以通过ProductService等获取商品详情来填充CartItem信息
            CartItem newItem = new CartItem();
            // 设置商品相关信息，比如名称、价格等（此处省略具体从数据库获取商品详情步骤）
            newItem.setProductId(productId);
            newItem.setProductName(product.getName());
            newItem.setImg(product.getImg());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(quantity);
            cartItems.add(newItem);
        }
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    @Override
    public void removeFromCart(String userId, Long productId) {
        String cartKey = CART_PREFIX + userId;
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        if (cart!= null) {
            List<CartItem> cartItems = cart.getCartItems();
            cartItems = cartItems.stream()
                    .filter(item ->!Objects.equals(item.getProductId(), productId))
                    .collect(Collectors.toList());
            cart.setCartItems(cartItems);
            redisTemplate.opsForValue().set(cartKey, cart);
        }
    }

    @Override
    public Cart getCart(String userId) {
        String cartKey = CART_PREFIX + userId;
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    @Override
    public void updateQuantity(String userId, Long productId, Integer newQuantity) {
        String cartKey = CART_PREFIX + userId;
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        if (cart!= null) {
            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem item : cartItems) {
                if (Objects.equals(item.getProductId(), productId)) {
                    item.setQuantity(newQuantity);
                    break;
                }
            }
            redisTemplate.opsForValue().set(cartKey, cart);
        }
    }
}
