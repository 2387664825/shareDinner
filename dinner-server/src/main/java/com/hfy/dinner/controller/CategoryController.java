package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hufayong
 * @date 2021/3/6 22:34
 */
@RestController
@RequestMapping(Const.API_V1 + "/wc/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{familyId}")
    public ResponseDo getCategory(@PathVariable Integer familyId) {
        return new ResponseDo(categoryService.getCategoryById(familyId));
    }
}
