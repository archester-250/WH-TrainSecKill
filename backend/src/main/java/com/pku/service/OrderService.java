package com.pku.service;

import com.pku.pojo.dto.OrderDTO;
import com.pku.pojo.vo.OrderVO;
import org.springframework.data.domain.Page;

public interface OrderService {
    public Page<OrderVO> getOrdersByUserId(Long userId, int page, int size);
    public Long addOrder(OrderDTO orderDTO);
    public Long cancelOrder(Long orderId);
}
