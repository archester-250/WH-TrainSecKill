package com.pku.controller;

import com.pku.pojo.entity.Cart;
import com.pku.properties.JwtProperties;
import com.pku.service.CartService;
import com.pku.service.CheckoutService;
import com.pku.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private JwtProperties jwtProperties;
    // 添加商品到购物车接口
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam Long productId, @RequestParam Integer quantity, @RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token, true);
        if(userId == -1){
            userId = JwtUtil.validateJWT(jwtProperties.getUserSecretKey(), token, false);
            if(userId == -1){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        cartService.addToCart(String.valueOf(userId), productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 从购物车删除商品接口
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam Long productId, @RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token, true);
        if(userId == -1){
            userId = JwtUtil.validateJWT(jwtProperties.getUserSecretKey(), token, false);
            if(userId == -1){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        cartService.removeFromCart(String.valueOf(userId), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查询购物车详情接口
    @GetMapping("")
    public ResponseEntity<Cart> getCart(@RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token, true);
        if(userId == -1){
            userId = JwtUtil.validateJWT(jwtProperties.getUserSecretKey(), token, false);
            if(userId == -1){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        Cart cart = cartService.getCart(String.valueOf(userId));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // 修改购物车商品数量接口
    @PutMapping("/update")
    public ResponseEntity<Void> updateQuantity(@RequestParam Long productId, @RequestParam Integer newQuantity, @RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token, true);
        if(userId == -1){
            userId = JwtUtil.validateJWT(jwtProperties.getUserSecretKey(), token, false);
            if(userId == -1){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        cartService.updateQuantity(String.valueOf(userId), productId, newQuantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 结算接口
    @PostMapping("/checkout")
    public ResponseEntity<BigDecimal> checkout(@RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token, true);
        if(userId == -1){
            userId = JwtUtil.validateJWT(jwtProperties.getUserSecretKey(), token, false);
            if(userId == -1){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        BigDecimal totalPrice = checkoutService.checkout(String.valueOf(userId));
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }
}