package com.pku.service;

import com.pku.pojo.entity.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    public void decrByStock(Long id);
    public SeckillGoods selectStockByGoodsId(Long id);
    public Long createSeckillGoods(SeckillGoods seckillGoods);
    public Long updateSeckillGoods(SeckillGoods seckillGoods);
    public void deleteSeckillGoods(Long id);
    public List<SeckillGoods> findAllSeckillGoods();
}
