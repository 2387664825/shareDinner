package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author hufayong
 * @date 2021/3/6 22:35
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
    @Select("select name from category where id = #{categoryId}")
    String selectNameById(Integer categoryId);
}
