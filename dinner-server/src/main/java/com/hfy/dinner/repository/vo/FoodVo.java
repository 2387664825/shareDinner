package com.hfy.dinner.repository.vo;

import com.hfy.dinner.repository.pojo.Food;
import lombok.Data;

/**
 * @author hfy
 */
@Data
public class FoodVo extends Food {
    private String familyName;

    private String categoryName;
}
