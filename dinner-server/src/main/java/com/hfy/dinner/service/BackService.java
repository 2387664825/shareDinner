package com.hfy.dinner.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.BackDao;
import com.hfy.dinner.repository.dto.BackQueryDto;
import com.hfy.dinner.repository.pojo.Back;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author hfy
 * @date 2021/5/9 20:10
 */
@Service
public class BackService {

    @Resource
    private BackDao backDao;

    public PageInfo<?> query(BackQueryDto queryDto) {
        int page = queryDto.getOffset() / queryDto.getLimit() + 1;
        PageHelper.startPage(page, queryDto.getLimit());
        List<Back> backs = backDao.selectByMap(new HashMap<>());
        return new PageInfo<>(backs);
    }
}
