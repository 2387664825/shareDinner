package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.OrderDao;
import com.hfy.dinner.repository.pojo.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hfy
 */
@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;


    public PageInfo<Order> getOrderList() {
        List<Order> orders = orderDao.selectList(new QueryWrapper<>());
        return new PageInfo<Order>(orders);
    }

    public void insert(Order order) {
        order.setCreateTime(new Date());
        order.setFamilyId(1);
        order.setStatus(1);
        order.setUserId(10002);
        orderDao.insert(order);
    }

    public PageInfo<Order> getOrderByUserId(Integer userId) {
        QueryWrapper<Order> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        List<Order> orders = orderDao.selectList(query);
        return new PageInfo<>(orders);
    }
}
