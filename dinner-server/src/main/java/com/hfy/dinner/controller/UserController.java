package com.hfy.dinner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.UserQueryDto;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.repository.pojo.User;
import com.hfy.dinner.service.UserService;
import com.hfy.dinner.util.HttpUrlConnectionUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufayong
 * @date 2021/3/6 21:57
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public User getUserInfo(@Param(value = "code") String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxa5242ad82a67b057&secret=46c2dcc0636b32ebd462d8eb2771f92c&grent_type=authorization_code&js_code=" + code;
        String result = HttpUrlConnectionUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        String openId = (String) jsonObject.get("openid");
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            User userNew = new User();
            userNew.setOpenId(openId);
            userService.insertUser(userNew);
            user = userNew;
        } else {
            userService.updateUser(user);
        }
        return user;
    }

    @PostMapping
    public void postUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @GetMapping("/list")
    public ResponseDo userList(UserQueryDto queryDto) {
        return new ResponseDo(userService.selectUserList(queryDto));
    }

    @GetMapping("/fb")
    public ResponseDo userFb(){
        return new ResponseDo(200,userService.getUserFb());
    }
    @GetMapping("/sf")
    public ResponseDo getProvince(){
        return new ResponseDo(200,userService.getProvince());
    }

    @GetMapping("/sc")
    public ResponseDo getFamilyId(Integer userId, String familyId) {
        return new ResponseDo(200, userService.getScByFamilyId(userId, familyId));
    }
}
