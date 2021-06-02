package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Family;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author hufayong
 * @date 2021/3/6 16:40
 */
@Mapper
public interface FamilyDao extends BaseMapper<Family> {

    @Select("select name from family where id = #{familyId} ")
    String selectNameById(Integer familyId);

    @Select("update family set number = number+1 where id = #{familyId} ")
    int updateNumber(Integer familyId);
}
