package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hfy.dinner.dao.UserDao;
import com.hfy.dinner.repository.dto.UserQueryDto;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.repository.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hufayong
 * @date 2021/3/6 21:57
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public ResponseDo getUser() {
        return new ResponseDo(200, userDao.selectById(10001));
    }

    public PageInfo<User> selectUserList(UserQueryDto queryDto) {
        int page=queryDto.getOffset() / queryDto.getLimit()+1;
        PageHelper.startPage(page, queryDto.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(queryDto.getName() != null && !"".equals(queryDto.getName())) {
            queryWrapper.like("name", queryDto.getName());
        }
        if(queryDto.getCity() != null && !"".equals(queryDto.getCity())) {
            queryWrapper.like("city", queryDto.getCity());
        }
        if(queryDto.getType() != null) {
            queryWrapper.eq("type", queryDto.getType());
        }
        if(queryDto.getLoginBeginTime()!=null){
            queryWrapper.ge("login_time",queryDto.getLoginBeginTime());
        }
        if(queryDto.getLoginEndTime()!=null){
            queryWrapper.le("login_time",queryDto.getLoginEndTime());
        }
        List<User> users = userDao.selectList(queryWrapper);
        return new PageInfo<User>(users);
    }

    public User getUserByOpenId(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        List<User> users = userDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(users)) {
            System.out.println("用户不存在");
            return null;
        }
        User user = users.get(0);
        return user;
    }

    public void insertUser(User userNew) {
        userDao.insert(userNew);
    }

    public void updateUser(User user) {
        user.setLoginTime(new Date());
        userDao.updateById(user);
    }
}
