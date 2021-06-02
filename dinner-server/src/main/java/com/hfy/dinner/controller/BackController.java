package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.BackQueryDto;
import com.hfy.dinner.repository.pojo.Back;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseDo back(@RequestBody Back back){
        return backService.update(back);
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Back back) {
        backService.insert(back);
    }

}
