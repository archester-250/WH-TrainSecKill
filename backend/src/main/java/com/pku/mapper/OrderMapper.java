package com.pku.mapper;

import com.pku.pojo.entity.Order;
import com.pku.pojo.entity.Product;
import com.pku.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    Long addOrder(Order order);
    void cancelOrder(Order order);
    List<OrderVO> selectOrderByUserId(Long userId,
                                      Integer offset,
                                      Integer pageSize);
    @Select("select * from t_order where id = #{id}")
    Order selectOrderById(Long id);
}
