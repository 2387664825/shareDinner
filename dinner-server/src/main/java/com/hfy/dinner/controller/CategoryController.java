package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.Category;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/family")
    public ResponseDo getCategoryById(@RequestParam(value = "id") Integer id) {
        return new ResponseDo(200, categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseDo insertOrUpdate(@RequestBody Category category) {
        return new ResponseDo(200, categoryService.insertOrUpdate(category));
    }

    @DeleteMapping("/{id}")
    public ResponseDo deleteByid(@PathVariable(value = "id") Integer id) {
        return new ResponseDo(200, categoryService.deleteById(id));
    }
}
