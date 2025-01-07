package com.pku.service;

import com.pku.pojo.entity.SeckillOrder;

import java.util.List;

public interface MQOrderService {
    public void createOrder(List<Long> list);
}
