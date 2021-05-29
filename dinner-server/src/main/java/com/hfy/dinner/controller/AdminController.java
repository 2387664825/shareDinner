package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.AdminQueryDto;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理系统
 * 管理员登录
 *
 * @author hfy
 */
@RequestMapping(Const.API_V1 + "/wc/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public ResponseDo loginUser(@RequestParam(value = "name") Integer name, @RequestParam(value = "password") String password) {
        return adminService.loginUser(name, password);
    }

    @GetMapping("/list")
    public ResponseDo adminList(AdminQueryDto queryDto) {
        return new ResponseDo(adminService.selectList(queryDto));
    }
}
