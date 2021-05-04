package com.hfy.dinner.repository.vo;

import com.hfy.dinner.repository.pojo.Order;
import lombok.Data;

/**
 * @author hfy
 * @date 2021/4/28 23:31
 */
@Data
public class OrderVo extends Order {
    private String userName;

    private String familyName;

    private String picture;

}
