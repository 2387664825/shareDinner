package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfy.dinner.dao.ProvinceDao;
import com.hfy.dinner.repository.pojo.Province;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hfy
 * @date 2021/5/11 23:42
 */
@Service
public class ProvinceService {
    @Resource
    private ProvinceDao provinceDao;


    public Object getArea(String city) {
        QueryWrapper<Province> provinceQueryWrapper = new QueryWrapper<>();
        provinceQueryWrapper.eq("city", city);
        List<Province> provinces = provinceDao.selectList(provinceQueryWrapper);
        return provinces;
    }
}
