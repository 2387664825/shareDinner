package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author hufayong
 * @date 2021/3/6 22:02
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select count(*) from user where to_days(create_time) = to_days(now()) and type = #{type} ")
    Integer selectCountUserDay(Integer type);

    @Select("select count(*) from user where type = #{type}")
    Integer selectCountALL(int type);
}
