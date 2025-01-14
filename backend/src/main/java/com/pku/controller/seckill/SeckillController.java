package com.pku.controller.seckill;
import com.pku.config.MyRabbitMQConfig;
import com.pku.pojo.dto.OrderDTO;
import com.pku.pojo.entity.Product;
import com.pku.pojo.entity.SeckillGoods;
import com.pku.pojo.entity.SeckillOrder;
import com.pku.properties.JwtProperties;
import com.pku.service.*;
import com.pku.utils.JwtUtil;
import com.pku.utils.RedisUtil;
import com.pku.pojo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/seckill")
public class SeckillController {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 使用redis+消息队列进行秒杀实现
     * @param token
     * @param goodsId
     * @return
     */
    @RequestMapping("/sec")
    @ResponseBody
    public ResponseEntity<?> sec(@RequestHeader("token") String token, @RequestParam(value = "goodsId") Long goodsId) {
        Jws<Claims> jws = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        Claims claims = jws.getPayload();
        Long userId = claims.get("userId", Long.class);
        log.info("参加秒杀的用户id是：{}，秒杀的商品id是：{}", userId, goodsId);
        String message = null;
        //检查库存
        Object data = redisService.getGoods(goodsId);
        if(data == null){
            SeckillGoods seckillGoods = seckillGoodsService.selectStockByGoodsId(goodsId);
            if(seckillGoods == null){
                return ResponseEntity.notFound().build();
            }else if(seckillGoods.getStockCount() <= 0){
                message = userId + "商品的库存量没有剩余或商品不存在,秒杀结束";
                return ResponseEntity.badRequest().body(message);
            }else if(LocalDateTime.now().isAfter(seckillGoods.getEndDate())){
                message = "秒杀活动已结束";
                return ResponseEntity.badRequest().body(message);
            }else if(LocalDateTime.now().isBefore(seckillGoods.getStartDate())){
                message = "秒杀活动还没开始捏";
                return ResponseEntity.badRequest().body(message);
            }else{
                redisService.putGoods(goodsId, seckillGoods.getStockCount());
            }
        }

        //检测用户是否已经购买
        if(redisService.hasPurchased(userId, goodsId)){
            message = "用户"+userId+"已经购买过商品"+goodsId;
            log.info(message);
            return ResponseEntity.badRequest().body(message);
        }

        //扣减商品
        Long decrByResult = redisService.decrByGoods(goodsId);
        log.info("redis缓存中商品{}剩余: {}", goodsId, decrByResult);
        if (decrByResult >= 0) {
            log.info("用户：{}秒杀该商品：{}库存有余，可以进行下订单操作", userId, goodsId);
            //发消息给库存消息队列，将库存数据减一
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, goodsId);
            List<Long> list = new ArrayList<>(){{
                add(userId);
                add(goodsId);
                }
            };
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, list);
            redisService.recordPurchase(userId, goodsId);
            message = "用户" + userId + " 秒杀->商品" + goodsId + "成功";
            return ResponseEntity.ok(message);
        } else {
            log.info("用户：{}秒杀时商品的库存量没有剩余,秒杀结束", userId);
            message = userId + "商品的库存量没有剩余,秒杀结束";
            return ResponseEntity.badRequest().body(message);
        }
    }

    @GetMapping("/secGoods")
    public ResponseEntity<?> getSecGoods(){
        return ResponseEntity.ok(seckillGoodsService.findAllSeckillGoods());
    }
}
