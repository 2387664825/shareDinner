package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员
 *
 * @author hfy
 */
@Mapper
public interface AdminDao extends BaseMapper<Admin> {
}
