package com.pku.service.Impl;

import com.pku.mapper.SeckillGoodsMapper;
import com.pku.pojo.entity.SeckillGoods;
import com.pku.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public void decrByStock(Long goodId) {
        synchronized(this) {
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectGoodstById(goodId);
            if (!CollectionUtils.isEmpty(seckillGoods)) {
                SeckillGoods seckillGood = seckillGoods.get(0);
                seckillGood.setStockCount(seckillGood.getStockCount() - 1);
                seckillGoodsMapper.updateSeckillGoods(seckillGood);
            }
        }
    }

    @Override
    public SeckillGoods selectStockByGoodsId(Long id) {
        List<SeckillGoods> goods = seckillGoodsMapper.selectGoodstById(id);
        if(goods.isEmpty()) return null;
        return goods.get(0);
    }

    @Override
    public Long createSeckillGoods(SeckillGoods seckillGoods) {
        return seckillGoodsMapper.insertSeckillGoods(seckillGoods);
    }

    @Override
    public Long updateSeckillGoods(SeckillGoods seckillGoods) {
        return seckillGoodsMapper.updateSeckillGoods(seckillGoods);
    }

    @Override
    public void deleteSeckillGoods(Long id) {
        seckillGoodsMapper.deleteSeckillGoods(id);
    }

    @Override
    public List<SeckillGoods> findAllSeckillGoods() {
        return seckillGoodsMapper.selectAllSeckillGoods();
    }
}
