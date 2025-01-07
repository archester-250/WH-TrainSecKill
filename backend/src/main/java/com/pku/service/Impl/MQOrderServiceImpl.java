package com.pku.service.Impl;

import com.pku.config.MyRabbitMQConfig;
import com.pku.mapper.SeckillOrderMapper;
import com.pku.mapper.UserMapper;
import com.pku.pojo.dto.OrderDTO;
import com.pku.pojo.entity.SeckillOrder;
import com.pku.service.MQOrderService;
import com.pku.service.OrderService;
import com.pku.service.SeckillGoodsService;
import com.pku.service.SeckillOrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@Slf4j
public class MQOrderServiceImpl implements MQOrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @RabbitListener(queues = MyRabbitMQConfig.ORDER_QUEUE)
    public void createOrder(List<Long> list) {
        Long userId = list.get(0);
        Long goodsId = list.get(1);
        //发消息给订单消息队列，创建订单
        OrderDTO orderDTO = OrderDTO.builder()
                .userId(userId)
                .goodsCount(1)
                .goodsId(goodsId)
                .build();
        try{
            Long orderId = orderService.addOrder(orderDTO);
            SeckillOrder seckillOrder = SeckillOrder.builder()
                    .userId(userId)
                    .orderId(orderId)
                    .goodsId(goodsId)
                    .build();
            log.info("收到订单消息，订单用户id为：{}，商品id为：{}", seckillOrder.getUserId(), seckillOrder.getGoodsId());
            try{
                seckillOrderMapper.insertSeckillOrder(seckillOrder);
//            if(!userMapper.selectUserById(seckillOrder.getUserId()).isEmpty())
//                seckillOrderMapper.insertSeckillOrder(seckillOrder);
//            else
//                log.info("用户不存在");
            }catch (Exception e){
                log.error("同步商品事件异常:{}");
            }
        }catch (Exception e){
            log.error("添加订单失败");
        }
    }
}
