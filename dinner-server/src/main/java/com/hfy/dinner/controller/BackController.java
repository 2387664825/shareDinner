package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.BackQueryDto;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hfy
 * @date 2021/5/9 20:10
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/back")
public class BackController {

    @Autowired
    private BackService backService;

    @GetMapping
    public ResponseDo query(BackQueryDto queryDto) {
        return new ResponseDo(backService.query(queryDto));
    }

}
