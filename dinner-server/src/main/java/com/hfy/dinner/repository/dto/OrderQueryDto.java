package com.hfy.dinner.repository.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author hfy
 * @date 2021/5/2 9:40
 */
@Data
public class OrderQueryDto extends PageBase {

    private String userName;

    private String familyName;

    private Integer type;

    private Date beginTime;

    private Date endTime;
}
