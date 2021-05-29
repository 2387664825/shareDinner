package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.AdminDao;
import com.hfy.dinner.repository.dto.AdminQueryDto;
import com.hfy.dinner.repository.pojo.Admin;
import com.hfy.dinner.repository.pojo.ResponseDo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员
 *
 * @author hfy
 */
@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public ResponseDo loginUser(Integer name, String password) {
        Admin admin = adminDao.selectById(name);
        if (admin == null) {
            return new ResponseDo(404, "账户不存在");
        } else if (password.equals(admin.getPassword())) {
            return new ResponseDo(200, "登录成功");
        } else {
            return new ResponseDo(202, "账户密码错误");
        }
    }

    public PageInfo<Admin> selectList(AdminQueryDto queryDto) {
        int page=queryDto.getOffset() / queryDto.getLimit()+1;
        PageHelper.startPage(page, queryDto.getLimit());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        List<Admin> admins = adminDao.selectList(queryWrapper);
        return new PageInfo<Admin>(admins);
    }
}
