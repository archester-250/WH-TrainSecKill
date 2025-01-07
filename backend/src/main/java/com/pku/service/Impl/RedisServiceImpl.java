package com.pku.service.Impl;

import com.pku.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String GOODS_KEY_PREFIX = "goods:";
    private final String USER_KEY_PREFIX = "user:";
    /**
     * 设置String键值对
     * @param key
     * @param value
     * @param millis
     */
    @Override
    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }

    @Override
    public void putGoods(Long goodsId, Object value) {
        put(GOODS_KEY_PREFIX + String.valueOf(goodsId), value, 1);
    }

    @Override
    public void putUser(Long userId, Object value) {
        put(USER_KEY_PREFIX + String.valueOf(userId), value, 1);
    }

    @Override
    public void delGoods(Long goodsId) {
        redisTemplate.delete(GOODS_KEY_PREFIX + String.valueOf(goodsId));
    }

    @Override
    public void recordPurchase(Long userId, Long goodsId) {
        redisTemplate.opsForSet().add(USER_KEY_PREFIX + String.valueOf(goodsId), String.valueOf(userId));
    }

    @Override
    public boolean hasPurchased(Long userId, Long goodsId) {
        Set<Object> purchasedUsers = redisTemplate.opsForSet().members(USER_KEY_PREFIX + String.valueOf(goodsId));
        return purchasedUsers != null && purchasedUsers.contains(String.valueOf(userId));
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getGoods(Long goodsId) {
        return get(GOODS_KEY_PREFIX + String.valueOf(goodsId));
    }

    @Override
    public Object getUser(Long userId) {
        return get(USER_KEY_PREFIX + String.valueOf(userId));
    }

    /**
     * 对指定key的键值减一
     * @param key
     * @return
     */
    @Override
    public Long decrBy(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrByGoods(Long goodsId) {
        return decrBy(GOODS_KEY_PREFIX + String.valueOf(goodsId));
    }
}
