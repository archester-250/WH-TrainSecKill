package com.pku.mapper;

import com.pku.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> searchProducts(
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax
    );
    @Select("select * from t_goods where id = #{productId}")
    Product selectProductById(Long productId);
}
