package com.pku.service.Impl;

import com.pku.mapper.SeckillOrderMapper;
import com.pku.pojo.entity.SeckillOrder;
import com.pku.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public void createOrder(SeckillOrder seckillOrder) {

        seckillOrderMapper.insertSeckillOrder(seckillOrder);
    }
}
