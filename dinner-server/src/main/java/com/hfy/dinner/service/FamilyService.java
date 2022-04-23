package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.dao.ProvinceDao;
import com.hfy.dinner.dao.UserDao;
import com.hfy.dinner.repository.dto.FamilyQueryDto;
import com.hfy.dinner.repository.pojo.Family;
import com.hfy.dinner.repository.pojo.Province;
import com.hfy.dinner.repository.pojo.User;
import com.hfy.dinner.util.DistanceUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private UserDao userDao;

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
        Family family = familyDao.selectById(familyId);
        if (family.getFamilyCount() <= family.getReceiveCount()) {
            family.setYy(1);
        } else {
            family.setYy(0);
        }
        return family;
    }

    public void inserintoFamily(Family family) {
        if (family.getId() != null) {
            if (family.getStatus() == 3) {
                userDao.setUserTypeByFamilyId(family.getId(), 1);
            } else if (family.getStatus() == 4) {
                userDao.setUserTypeByFamilyId(family.getId(), 0);
            }
            familyDao.updateById(family);
        } else {
            family.setStatus(1);
            familyDao.insert(family);
            Integer id = familyDao.getLastId();
            User user = new User();
            user.setId(family.getUserId());
            user.setFamilyId(id);
            userDao.updateById(user);
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
            if (x != null && y != null) {
                family.setJl(DistanceUtil.getDistance(x, y, family.getWzX(), family.getWzy()));
            }
        }
        Collections.sort(families);
        return new PageInfo<Family>(families);
    }

    public List<Family> getByUserid(Integer userId) {
        User user = userDao.selectById(userId);
        String[] split = user.getConcern().split(";");
        List<Integer> familyId = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            familyId.add(Integer.valueOf(split[i]));
        }
        QueryWrapper<Family> query = new QueryWrapper<>();
        query.in("id", familyId);
        List<Family> families = familyDao.selectList(query);
        return families;
    }
}
