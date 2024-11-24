package com.pku.controller.product;


import com.pku.pojo.entity.Product;
import com.pku.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/user/products")
public class ProductController {
    @Autowired private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "3") int size,
                                                     @RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) Long category,
                                                     @RequestParam(required = false) BigDecimal priceMin,
                                                     @RequestParam(required = false) BigDecimal priceMax) {
        log.info("获取商品列表，页码：{}，每页数量：{}", page-1, size);//用户看到的从1开始，前端看到的从0开始
        return ResponseEntity.ok(productService.findProducts(page, size, keyword, category, priceMin, priceMax));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(product);
        }
    }

}
