package com.hfy.dinner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfy.dinner.repository.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hfy
 * @date 2021/5/24 0:30
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
