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
        List<Family> families = familyDao.selectByMap(new HashMap<>());
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

    public PageInfo<Family> queryByQy(Double x, Double y) {
        Province province = null;
        try {
            String district = DistanceUtil.getDistrict(x, y);
            QueryWrapper<Province> provinceQueryWrapper = new QueryWrapper<Province>();
            provinceQueryWrapper.eq("name", district);
            List<Province> provinces = provinceDao.selectList(provinceQueryWrapper);
            if (!CollectionUtils.isEmpty(provinces)) {
                province = provinces.get(0);
            }

        } catch (Exception e) {
            System.out.println("根据座标，获取位置失败");
            e.printStackTrace();
        }
        QueryWrapper<Family> familyQueryWrapper = new QueryWrapper<>();
        familyQueryWrapper.eq("location_code", province.getCode());
        List<Family> families = familyDao.selectList(familyQueryWrapper);
        for (Family family : families) {
            family.setStatusT(status.get(family.getStatus()));
            if (x != null && y != null)
                family.setJl(DistanceUtil.getDistance(x, y, family.getWzX(), family.getWzy()));
        }
        Collections.sort(families);
        return new PageInfo<Family>(families);
    }
}
