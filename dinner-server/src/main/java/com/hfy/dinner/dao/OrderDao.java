package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hfy
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {
}
