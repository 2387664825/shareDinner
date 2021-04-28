package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Food;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hufayong
 * @date 2021/3/6 22:08
 */
@Mapper
public interface FoodDao extends BaseMapper<Food> {
}
