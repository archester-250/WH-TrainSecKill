package com.pku.service;

import org.springframework.data.relational.core.sql.In;

public interface MQGoodsService {
    public void decrByStock(Long goodsId, Integer quantity);
}
