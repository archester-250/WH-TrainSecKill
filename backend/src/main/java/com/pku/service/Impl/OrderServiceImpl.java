package com.pku.service.Impl;


import com.pku.context.BaseContext;
import com.pku.mapper.OrderMapper;
import com.pku.mapper.ProductMapper;
import com.pku.pojo.dto.OrderDTO;
import com.pku.pojo.entity.Order;
import com.pku.pojo.entity.Product;
import com.pku.pojo.vo.OrderVO;
import com.pku.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<OrderVO> getOrdersByUserId(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<OrderVO> orders = orderMapper.selectOrderByUserId(userId, offset, size);
        int total = orders.size();
        return new PageImpl<>(orders, PageRequest.of(page-1, size), total);
    }

    @Transactional
    @Override
    public Long addOrder(OrderDTO orderDTO) {
        Product product = productMapper.selectProductById(orderDTO.getGoodsId());
        if(product == null) {
            return (long) -1;
        }
        if(product.getStock()!=-1){
            if(product.getStock()<orderDTO.getGoodsCount()){
                return (long) -2;
            }else{
                product.setStock(product.getStock()-orderDTO.getGoodsCount());
            }
        }
        productMapper.updateProduct(product);
        Order order = Order.builder()
                .userId(orderDTO.getUserId())
//                .userId(BaseContext.getCurrentId())
                .goodsId(orderDTO.getGoodsId())
                .deliveryAddrId((long)0)
                .goodsName(product.getName())
                .goodsCount(orderDTO.getGoodsCount())
                .goodsPrice(product.getPrice())
                .orderChannel(1)
                .status(Order.WAITING_PAYING)
                .createDate(LocalDateTime.now())
                .payDate(LocalDateTime.now())
                .build();
        orderMapper.addOrder(order);
        return order.getId();
    }

    @Transactional
    @Override
    public Long cancelOrder(Long orderId) {
        Order order = orderMapper.selectOrderById(orderId);
        if(order == null) {
            return (long) -1;
        }
        if(order.getStatus() == Order.CANCELLED) {
            return (long) -2;
        }
        Product product  = productMapper.selectProductById(order.getGoodsId());
        if(product.getStock() != -1){
            product.setStock(product.getStock()+order.getGoodsCount());
            productMapper.updateProduct(product);
        }
        order.setStatus(Order.CANCELLED);
        orderMapper.cancelOrder(order);
        return (long) 1;
    }
}
