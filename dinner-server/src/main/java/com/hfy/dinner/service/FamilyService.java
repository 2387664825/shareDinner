package com.hfy.dinner.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.FamilyDao;
import com.hfy.dinner.repository.dto.FamilyQueryDto;
import com.hfy.dinner.repository.pojo.Family;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public PageInfo<?> query(FamilyQueryDto queryDto) {
        int page=queryDto.getOffset() / queryDto.getLimit()+1;
        PageHelper.startPage(page, queryDto.getLimit());
        List<Family> families = familyDao.selectByMap(new HashMap<>());
        for (Family family : families) {
            family.setStatusT(status.get(family.getStatus()));
        }
        return new PageInfo<>(families);
    }

}
