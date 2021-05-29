package com.hfy.dinner.repository.dto;

import lombok.Data;

/**
 * @author hfy
 * @date 2021/5/9 20:15
 */
@Data
public class BackQueryDto extends PageBase {
    private String name;
    private Integer type;
}
