package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.dto.FoodQueryDto;
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

    @Select("select * from food where family_id = #{familyId} ")
    List<Food> selectBYFamilyId(Integer familydId);

    List<Food> selectByDto(FoodQueryDto queryDto);

    @Select("select * from food where family_id = #{familyId} and today = 1")
    List<Food> selectByFaimlyIdToday(Integer familyId);

    @Select("update food set today = 0 where family_id = #{familyId} ")
    void clearToDay(Integer familyId);

    @Select("update food set today = 1 where id = #{id} ")
    void setToDay(Integer id);
}
