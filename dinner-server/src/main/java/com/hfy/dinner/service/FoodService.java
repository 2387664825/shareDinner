package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.CategoryDao;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.dao.FoodDao;
import com.hfy.dinner.repository.pojo.Food;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.repository.vo.FoodVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hufayong
 * @date 2021/3/6 22:07
 */
@Service
public class FoodService {
    @Resource
    private FoodDao foodDao;

    @Resource
    private FamilyDao familyDao;

    @Resource
    private CategoryDao categoryDao;

    public ResponseDo getFoodList() {
        List<Food> foodList = foodDao.selectList(new QueryWrapper<>());
        List<FoodVo> foodVos = new ArrayList<>();
        for (Food food : foodList) {
            FoodVo foodVo = new FoodVo();
            BeanUtils.copyProperties(food, foodVo);
            foodVo.setFamilyName(familyDao.selectNameById(food.getFamilyId()));
            foodVo.setCategoryName(categoryDao.selectNameById(food.getCategoryId()));
            foodVos.add(foodVo);
        }
        return new ResponseDo(new PageInfo<FoodVo>(foodVos));
    }
}
