package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.FoodQueryDto;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hufayong
 * @date 2021/3/6 22:07
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/list")
    public ResponseDo getFoodList(FoodQueryDto queryDto) {
        return new ResponseDo(foodService.getFoodList(queryDto));
    }
}
