package com.pku;
import com.pku.pojo.entity.SeckillGoods;
import com.pku.service.RedisService;
import com.pku.service.SeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
@SpringBootApplication
@Slf4j
public class WH_SeckillApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(WH_SeckillApplication.class, args);
    }

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 缓存预热逻辑，将秒杀商品数据加载到Redis缓存中
        List<SeckillGoods> seckillGoodsList = seckillGoodsService.findAllSeckillGoods();  // 获取所有秒杀商品列表，这里假设SeckillGoodsService有对应的方法
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            redisService.putGoods(seckillGoods.getGoodsId(), seckillGoods.getStockCount());
            log.info("商品{}已经预载redis缓存完毕", seckillGoods.getGoodsId());
        }
        log.info("缓存预热完成，已将秒杀商品数据加载到Redis缓存中。");
    }
}
