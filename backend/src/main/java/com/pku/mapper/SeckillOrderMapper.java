package com.pku.mapper;

import com.pku.pojo.entity.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillOrderMapper {
    void insertSeckillOrder(SeckillOrder seckillOrder);
}
