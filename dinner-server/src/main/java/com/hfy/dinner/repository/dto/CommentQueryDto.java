package com.hfy.dinner.repository.dto;

import lombok.Data;

/**
 * @author hfy
 * @date 2021/5/2 9:41
 */
@Data
public class CommentQueryDto extends PageBase {
    private Integer familyId;
}
