package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hufayong
 * @date 2021/3/6 22:08
 */
@Mapper
public interface FoodDao extends BaseMapper<Food> {
    @Select("select * from food where family_id = #{familyId} and category_id = #{categoryId}")
    List<Food> selectByFaimlyIdAndCategoryId(@Param(value = "familyId") Integer familyId, @Param(value = "categoryId") Integer id);
}
