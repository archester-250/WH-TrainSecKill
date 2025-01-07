package com.pku.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    public void put(String key, Object value, long millis);
    public void putGoods(Long goodsId, Object value);
    public void putUser(Long userId, Object value);
    public void delGoods(Long goodsId);
    public void recordPurchase(Long userId, Long goodsId);
    public boolean hasPurchased(Long userId, Long goodsId);
    public Object get(String key);
    public Object getGoods(Long goodsId);
    public Object getUser(Long userId);
    public Long decrBy(String key);
    public Long decrByGoods(Long goodsId);
}
