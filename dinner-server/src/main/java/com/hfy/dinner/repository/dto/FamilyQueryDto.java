package com.hfy.dinner.repository.dto;

import lombok.Data;

/**
 * @author hufayong
 * @date 2021/3/6 16:29
 */
@Data
public class FamilyQueryDto extends PageBase{
    private String name;
    private String city;
    private String type;
}
