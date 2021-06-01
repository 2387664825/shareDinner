package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.OrderQueryDto;
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
    public ResponseDo orderList(OrderQueryDto queryDto) {
        return new ResponseDo(orderService.getOrderList(queryDto));
    }

    @GetMapping("/user")
    public ResponseDo getOrderByUserId(Integer userId, Integer type, Integer familyId) {
        return new ResponseDo(orderService.getOrderById(type, familyId, userId));
    }

    @GetMapping("/one")
    public ResponseDo getOrderByUserIdAndFamilyId(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "familyId") Integer familyId) {
        Order orderByUserIdAndFamilyId = orderService.getOrderByUserIdAndFamilyId(userId, familyId);
        if (orderByUserIdAndFamilyId != null) {
            return new ResponseDo(200, orderByUserIdAndFamilyId);
        }
        return new ResponseDo(404, "不存在订单");
    }

    @PostMapping
    public ResponseDo createOrder(@RequestBody Order order) {

        orderService.insert(order);
        return new ResponseDo(200, "创建成功");
    }

    @PostMapping("/cancel")
    public ResponseDo cancelOrder(@RequestBody Order order) {
        boolean update = orderService.cancelOrder(order);
        if (update) {
            return new ResponseDo(200, "更改成功");
        } else {
            return new ResponseDo(500, "更改失败");
        }
    }

    @GetMapping("/seven")
    public ResponseDo getSevenDay(){
        return new ResponseDo(200, orderService.getSevenDay());
    }
    @GetMapping("/indexData")
    public ResponseDo getIndexData(){
        return new ResponseDo(200,orderService.getIndexData());
    }
}
