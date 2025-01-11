package com.pku.service.Impl;

import com.pku.config.MyRabbitMQConfig;
import com.pku.mapper.SeckillGoodsMapper;
import com.pku.pojo.entity.SeckillGoods;
import com.pku.service.MQGoodsService;
import com.pku.service.SeckillGoodsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Transactional
@Service
@Slf4j
public class MQGoodsServiceImpl implements MQGoodsService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    /**
     * 监听库存消息队列，并消费
     * @param goodsId
     */
    @Override
    @RabbitListener(queues = MyRabbitMQConfig.STORY_QUEUE)
    public void decrByStock(Long goodsId, Integer quantity) {
        log.info("库存消息队列收到的消息商品id是：{}", goodsId);
        /**
         * 调用数据库service给数据库对应商品库存减一
         */
        synchronized(this) {
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectGoodstById(goodsId);
            if (!CollectionUtils.isEmpty(seckillGoods)) {
                SeckillGoods seckillGood = seckillGoods.get(0);
                seckillGood.setStockCount(seckillGood.getStockCount() - quantity);
                seckillGoodsMapper.updateSeckillGoods(seckillGood);
            }
        }
    }
}
