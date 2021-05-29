package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.CategoryDao;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.dao.FoodDao;
import com.hfy.dinner.dao.UserDao;
import com.hfy.dinner.repository.dto.FoodQueryDto;
import com.hfy.dinner.repository.pojo.Food;
import com.hfy.dinner.repository.pojo.User;
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
    private UserDao userDao;

    @Resource
    private FoodDao foodDao;

    @Resource
    private FamilyDao familyDao;

    @Resource
    private CategoryDao categoryDao;

    public PageInfo<FoodVo> getFoodList(FoodQueryDto queryDto) {
        int page=queryDto.getOffset() / queryDto.getLimit()+1;
        PageHelper.startPage(page, queryDto.getLimit());
        List<Food> foodList = foodDao.selectByDto(queryDto);
        PageInfo foodPageInfo = new PageInfo<>(foodList);
        List<FoodVo> foodVos = new ArrayList<>();
        for (Food food : foodList) {
            FoodVo foodVo = new FoodVo();
            BeanUtils.copyProperties(food, foodVo);
            foodVo.setFamilyName(familyDao.selectNameById(food.getFamilyId()));
            foodVo.setCategoryName(categoryDao.selectNameById(food.getCategoryId()));
            foodVos.add(foodVo);
        }
        foodPageInfo.setList(foodVos);
        return foodPageInfo;
    }

    public Object getFoodByUserId(Integer userId) {
        User user = userDao.selectById(userId);
        List<Food> foodList = new ArrayList<>();
        if (user.getFamilyId() != null) {
            foodList = foodDao.selectBYFamilyId(user.getFamilyId());
        }
        return foodList;
    }

    public Object getFoodById(Integer id) {
        return foodDao.selectById(id);
    }

    public boolean updateOrInsertFood(Food food) {
        try {
            if (food.getId() != null) {
                foodDao.updateById(food);
            } else {
                foodDao.insert(food);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
