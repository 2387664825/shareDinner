package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.FoodQueryDto;
import com.hfy.dinner.repository.pojo.Food;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user")
    public ResponseDo getFoodByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ResponseDo(200, foodService.getFoodByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseDo getFoodById(@PathVariable(value = "id") Integer id) {
        return new ResponseDo(200, foodService.getFoodById(id));
    }

    @PostMapping
    public ResponseDo updateFood(@RequestBody Food food) {
        if (foodService.updateOrInsertFood(food)) {
            return new ResponseDo(200, "更新/插入成功");
        }
        return new ResponseDo(400, "操作错误");
    }
}
