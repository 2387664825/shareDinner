package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.dto.OrderQueryDto;
import com.hfy.dinner.repository.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hfy
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {
    @Select("select * from user_order where user_id = #{userId} and family_id = #{familyId} and to_days(create_time) = to_days(now()) ")
    List<Order> selectToday(@Param(value = "userId") Integer userId, @Param(value = "familyId") Integer familyId);

    @Select("update user_order set status = 0 where  user_id = #{userId} and family_id = #{familyId} and to_days(create_time) = to_days(now()) ")
    void updateByOrder(@Param(value = "userId") Integer userId, @Param(value = "familyId") Integer familyId);

    List<Order> selectByDto(OrderQueryDto queryDto);

    @Select("select * from user_order where date_sub(curdate(),interval 7 day) <= date(create_time) order by create_time ")
    List<Order> getSevenDay();

    @Select("select count(*) from user_order where to_days(create_time) = to_days(now())")
    Integer selectCountDay();

    @Select("select count(*) from user_order")
    Integer selectCountAll();
}
