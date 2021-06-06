package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.CategoryDao;
import com.hfy.dinner.dao.FoodDao;
import com.hfy.dinner.repository.pojo.Category;
import com.hfy.dinner.repository.pojo.Food;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hufayong
 * @date 2021/3/6 22:34
 */
@Service
public class CategoryService {
    @Resource
    private CategoryDao categoryDao;

    @Resource
    private FoodDao foodDao;

    public PageInfo<?> getCategoryById(Integer familyId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("family_id", familyId);
        wrapper.orderByAsc("sort");
        List<Category> lists = categoryDao.selectList(wrapper);
        if (!CollectionUtils.isEmpty(lists)) {
            for (Category category : lists) {
                List<Food> foodList = new ArrayList<>();
                if (category.getHide() == 1) {
                    foodList = foodDao.selectByFaimlyIdToday(familyId);
                } else {
                    foodList = foodDao.selectByFaimlyIdAndCategoryId(familyId, category.getId());
                }
                category.setFoods(foodList);
            }
        }
        return new PageInfo<>(lists);
    }

    public List<Category> getCategory(Integer id) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", id);
        queryWrapper.eq("hide", 0);
        queryWrapper.orderByAsc("sort");
        List<Category> lists = categoryDao.selectList(queryWrapper);
        return lists;
    }

    public boolean deleteById(Integer id) {
        int i = categoryDao.deleteById(id);
        return i == 1;
    }

    public Object insertOrUpdate(Category category) {
        if (category.getId() != null) {
            categoryDao.updateById(category);
        } else {
            categoryDao.insert(category);
        }
        return true;
    }
}
