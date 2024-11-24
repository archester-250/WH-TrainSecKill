package com.pku.service;

import com.pku.pojo.entity.Product;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;

public interface ProductService {
    public Page<Product> findProducts(int page, int size, String keyword, Long categoryId, BigDecimal priceMin, BigDecimal priceMax);

    public Product findProductById(Long id);
}
