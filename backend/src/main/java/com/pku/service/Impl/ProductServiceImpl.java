package com.pku.service.Impl;

import com.pku.mapper.ProductMapper;
import com.pku.pojo.entity.Product;
import com.pku.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<Product> findProducts(int page, int size, String keyword, Long categoryId, BigDecimal priceMin, BigDecimal priceMax) {
        int offset = (page - 1) * size;
        List<Product> products = productMapper.searchProducts(offset, size, keyword, categoryId, priceMin, priceMax);
        int total = products.size();
        return new PageImpl<>(products, PageRequest.of(page-1, size), total);
    }

    @Override
    public Product findProductById(Long id) {
        return productMapper.selectProductById(id);
    }
}
