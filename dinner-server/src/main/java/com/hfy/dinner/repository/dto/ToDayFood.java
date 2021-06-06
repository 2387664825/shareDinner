package com.hfy.dinner.repository.dto;

import com.hfy.dinner.repository.pojo.Food;
import lombok.Data;

import java.util.List;

/**
 * @author hfy
 * @date 2021/6/3 1:58
 */
@Data
public class ToDayFood {
    private String beginTime;

    private String endTime;

    private Integer number;

    private Float price;

    private List<Food> foods;

    private Integer familyId;

}
