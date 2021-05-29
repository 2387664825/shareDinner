package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.dao.ProvinceDao;
import com.hfy.dinner.repository.dto.FamilyQueryDto;
import com.hfy.dinner.repository.pojo.Family;
import com.hfy.dinner.repository.pojo.Province;
import com.hfy.dinner.util.DistanceUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class FamilyService {

    private static HashMap<Integer, String> status;

    static {
        status = new HashMap<>();
        status.put(1, "申请中");
        status.put(2, "通过");
        status.put(3, "运营中");
        status.put(4, "关闭");
        status.put(0, "拒绝");
    }

    @Resource
    private FamilyDao familyDao;
    @Resource
    private ProvinceDao provinceDao;

    public PageInfo<?> query(FamilyQueryDto queryDto) {
        int page = queryDto.getOffset() / queryDto.getLimit() + 1;
        PageHelper.startPage(page, queryDto.getLimit());
        QueryWrapper<Family> queryWrapper = new QueryWrapper<>();
        if(queryDto.getName() != null && !"".equals(queryDto.getName())) {
            queryWrapper.like("name", queryDto.getName());
        }
        if(queryDto.getCity() != null && !"".equals(queryDto.getCity())) {
            queryWrapper.like("location", queryDto.getCity());
        }
        if(queryDto.getType() != null) {
            queryWrapper.eq("status", queryDto.getType());
        }
        List<Family> families = familyDao.selectList(queryWrapper);
        for (Family family : families) {
            family.setStatusT(status.get(family.getStatus()));
        }
        return new PageInfo<>(families);
    }

    public Family getById(Integer familyId) {
        return familyDao.selectById(familyId);
    }

    public void inserintoFamily(Family family) {
        if (family.getId() != null) {
            familyDao.updateById(family);
        } else {
            family.setStatus(0);
            familyDao.insert(family);
        }
    }

    //search 从店铺名字进行搜索，
    public PageInfo<Family> queryByQy(Double x, Double y, String search) {
        Province province = null;
        QueryWrapper<Province> provinceQueryWrapper = new QueryWrapper<Province>();
        List<Province> provinces = null;
        try {
            String district = DistanceUtil.getDistrict(x, y);
            provinceQueryWrapper.eq("name", district);
            provinces = provinceDao.selectList(provinceQueryWrapper);
            if (!CollectionUtils.isEmpty(provinces)) {
                province = provinces.get(0);
            }

        } catch (Exception e) {
            System.out.println("根据座标，获取位置失败");
            e.printStackTrace();
        }
        QueryWrapper<Family> lambda = new QueryWrapper<Family>();
        if (province == null) {
            province = provinceDao.selectById("2268");
        }
        lambda.eq("location_code", province.getCode());
        if (search != null && !"".equals(search)) {
            lambda.like("name", search);
        }
        List<Family> families = familyDao.selectList(lambda);
        for (Family family : families) {
            family.setStatusT(status.get(family.getStatus()));
            if (x != null && y != null)
                family.setJl(DistanceUtil.getDistance(x, y, family.getWzX(), family.getWzy()));
        }
        Collections.sort(families);
        return new PageInfo<Family>(families);
    }
}
