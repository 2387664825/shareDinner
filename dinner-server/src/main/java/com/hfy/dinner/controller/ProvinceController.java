package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hfy
 * @date 2021/5/11 23:41
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/area")
    public ResponseDo getArea(String city) {
        return new ResponseDo(200, provinceService.getArea(city));
    }
}
