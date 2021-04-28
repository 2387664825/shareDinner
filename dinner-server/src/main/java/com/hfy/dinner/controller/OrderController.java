package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.Order;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hfy
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseDo orderList() {
        return new ResponseDo(orderService.getOrderList());
    }

    @GetMapping("/user")
    public ResponseDo getOrderByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ResponseDo(orderService.getOrderByUserId(userId));
    }

    @PostMapping
    public ResponseDo createOrder(@RequestBody Order order) {
        orderService.insert(order);
        return new ResponseDo(200, "创建成功");
    }
}
