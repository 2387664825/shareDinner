package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Admin;
import com.hfy.dinner.repository.pojo.China;
import org.apache.ibatis.annotations.Mapper;

/**
 * 省份数据
 *
 * @author hfy
 */
@Mapper
public interface ChinaDao extends BaseMapper<China> {
}
