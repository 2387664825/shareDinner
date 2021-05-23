package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.Comment;
import com.hfy.dinner.repository.pojo.ResponseDo;
import com.hfy.dinner.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hfy
 * @date 2021/5/24 0:29
 */
@RestController(Const.API_V1 + "/wc/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseDo selectByFamilyId(Integer familyId) {
        return new ResponseDo(200, commentService.selectByFamilyId(familyId));
    }

    @PostMapping
    public ResponseDo insert(@RequestBody Comment comment) {
        commentService.insert(comment);
        return new ResponseDo(200, "插入成功");
    }
}
