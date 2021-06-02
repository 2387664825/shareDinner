package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfy.dinner.dao.CommentDao;
import com.hfy.dinner.dao.OrderDao;
import com.hfy.dinner.dao.UserDao;
import com.hfy.dinner.repository.pojo.Comment;
import com.hfy.dinner.repository.pojo.Order;
import com.hfy.dinner.repository.pojo.User;
import com.hfy.dinner.repository.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hfy
 * @date 2021/5/24 0:30
 */
@Service
public class CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private UserDao userDao;
    @Resource
    private OrderDao orderDao;

    @Transactional
    public void insert(Comment comment) {
        comment.setCreateTime(new Date());
        comment.setType(1);
        Order order = new Order();
        order.setId(comment.getOrderId());
        order.setStatus(4);
        orderDao.updateById(order);
        commentDao.insert(comment);
    }

    public List<CommentVo> selectByFamilyId(Integer familyId) {
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.eq("family_id", familyId);
        List<Comment> comments = commentDao.selectList(query);
        List<CommentVo> lists = new ArrayList<>();
        for(Comment comment:comments){
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            User user = userDao.selectById(commentVo.getUserId());
            commentVo.setUserName(user.getName());
            commentVo.setPicture(user.getPicture());
            lists.add(commentVo);
        }
        return lists;

    }
}
