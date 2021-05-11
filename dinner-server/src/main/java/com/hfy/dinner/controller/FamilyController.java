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

    /**
     * 后台分页查询
     *
     * @param queryDto
     * @return
     */
    @GetMapping
    public ResponseDo query(FamilyQueryDto queryDto) {
        return new ResponseDo(familyService.query(queryDto));
    }

    /**
     * 区域查询
     *
     * @param x 纬度
     * @param y 经度
     * @return
     */
    @GetMapping("/qy")
    public ResponseDo queryByqy(Double x, Double y) {
        return new ResponseDo(familyService.queryByQy(x, y));
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
