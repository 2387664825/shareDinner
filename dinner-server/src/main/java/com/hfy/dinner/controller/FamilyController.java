package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.dto.FamilyQueryDto;
import com.hfy.dinner.repository.pojo.Family;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 家庭店铺
 *
 * @author hufayong
 * @date 2021/3/6 16:18
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/family")
public class FamilyController {
    @Autowired
    private FamilyService familyService;

    @GetMapping
    public ResponseDo query(FamilyQueryDto queryDto) {
        return new ResponseDo(familyService.query(queryDto));
    }

    @GetMapping(value = "/id")
    public ResponseDo queryById(Integer familyId) {
        return new ResponseDo(200, familyService.getById(familyId));
    }

    @PostMapping(value = "/insert")
    public ResponseDo insertFaimly(@RequestBody Family family) {
        familyService.inserintoFamily(family);
        return new ResponseDo(200, "插入成功");
    }

}
