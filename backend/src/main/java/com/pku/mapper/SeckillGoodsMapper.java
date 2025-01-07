package com.pku.mapper;

import com.pku.pojo.entity.Product;
import com.pku.pojo.entity.SeckillGoods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeckillGoodsMapper {
    Long updateSeckillGoods(SeckillGoods seckillGoods);

    @Select("select * from t_seckill_goods")
    List<SeckillGoods> selectAllSeckillGoods();

    @Select("select * from t_seckill_goods where goods_id = #{goodsId}")
    List<SeckillGoods> selectGoodstById(Long goodsId);

    @Insert("INSERT INTO t_seckill_goods (goods_id, seckill_price, stock_count, start_date, end_date)  VALUES (#{goodsId}, #{seckillPrice}, #{stockCount}, #{startDate}, #{endDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSeckillGoods(SeckillGoods goods);

    @Delete("DELETE FROM t_seckill_goods WHERE id = #{id}")
    void deleteSeckillGoods(Long id);
}
