package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfy.dinner.dao.CommentDao;
import com.hfy.dinner.repository.pojo.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hfy
 * @date 2021/5/24 0:30
 */
@Service
public class CommentService {
    @Resource
    private CommentDao commentDao;


    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    public List<Comment> selectByFamilyId(Integer familyId) {
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.eq("family_id", familyId);
        List<Comment> comments = commentDao.selectList(query);
        return comments;

    }
}
