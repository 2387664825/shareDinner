package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.dao.OrderDao;
import com.hfy.dinner.dao.UserDao;
import com.hfy.dinner.repository.dto.OrderQueryDto;
import com.hfy.dinner.repository.pojo.Family;
import com.hfy.dinner.repository.pojo.Order;
import com.hfy.dinner.repository.pojo.User;
import com.hfy.dinner.repository.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hfy
 */
@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private FamilyDao familyDao;


    public PageInfo<OrderVo> getOrderList(OrderQueryDto queryDto) {
        int page=queryDto.getOffset() / queryDto.getLimit()+1;
        PageHelper.startPage(page, queryDto.getLimit());
        List<Order> orders = orderDao.selectByDto(queryDto);
        PageInfo orderPageInfo = new PageInfo<>(orders);
        orderPageInfo.setList(convertVo(orders, null));
        return orderPageInfo;
    }

    @Transactional
    public void insert(Order order) {
        order.setCreateTime(new Date());
        order.setStatus(1);
        Family family = familyDao.selectById(order.getFamilyId());
        // 并发，是否会产生数据脏读？
        family.setReceiveCount(family.getReceiveCount() + 1);
        familyDao.updateById(family);
        orderDao.insert(order);
    }

    public PageInfo<OrderVo> getOrderById(Integer type, Integer familyId, Integer userId) {
        QueryWrapper<Order> query = new QueryWrapper<>();
        if (type == 0) {
            query.eq("user_id", userId);
        } else {
            query.eq("family_id", familyId);
        }
        query.orderByDesc("create_time");
        List<Order> orders = orderDao.selectList(query);
        return new PageInfo<>(convertVo(orders, type));
    }

    private List<OrderVo> convertVo(List<Order> orders, Integer type) {
        List<OrderVo> lists = new ArrayList<>();
        for (Order order : orders) {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            User user = userDao.selectById(order.getUserId());
            Family family = familyDao.selectById(order.getFamilyId());
            orderVo.setFamilyName(family.getName());
            orderVo.setUserName(user.getName());
            if (type != null && type == 1) {
                orderVo.setPicture(user.getPicture());
            } else {
                orderVo.setPicture(family.getImgLocation());
            }
            orderVo.setLocation(family.getLocation());
            lists.add(orderVo);
        }
        return lists;
    }

    public Order getOrderByUserIdAndFamilyId(Integer userId, Integer familyId) {
        List<Order> orders = orderDao.selectToday(userId, familyId);
        if (!CollectionUtils.isEmpty(orders)) {
            return orders.get(0);
        }
        return null;
    }

    /**
     * 取消订单
     *
     * @param order
     * @return
     */
    public boolean cancelOrder(Order order) {
        if (order.getUserId() == null) {
            System.out.println("订单 用户id 不能为空");
            return false;
        }
        if (order.getFamilyId() == null) {
            System.out.println("订单 店铺id 不能为空");
            return false;
        }
        Family family = familyDao.selectById(order.getFamilyId());
        family.setReceiveCount(family.getReceiveCount() - 1);
        familyDao.updateById(family);
        orderDao.updateByOrder(order.getUserId(), order.getFamilyId());
        return true;
    }

    public List<Order> getSevenDay() {
        return orderDao.getSevenDay();
    }

    public List<Integer> getIndexData() {
        List<Integer> lists = new ArrayList<>();
        lists.add(userDao.selectCountUserDay(0));
        lists.add(userDao.selectCountUserDay(1));
        lists.add(orderDao.selectCountDay());
        lists.add(userDao.selectCountALL(0));
        lists.add(userDao.selectCountALL(1));
        lists.add(orderDao.selectCountAll());
        return lists;
    }

    public boolean update(Order order) {
        if (order.getStatus() == 3) {
            familyDao.updateNumber(order.getFamilyId());
        }
        return orderDao.updateById(order) == 1;
    }
}
