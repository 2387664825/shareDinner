package com.hfy.dinner.repository.vo;

import com.hfy.dinner.repository.pojo.Comment;
import lombok.Data;

/**
 * @author hfy
 * @date 2021/5/24 22:30
 */
@Data
public class CommentVo extends Comment {

    private String userName;

    private String picture;
}
