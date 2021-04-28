package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author hufayong
 * @date 2021/3/6 22:35
 */
@Data
@TableName(value = "category")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "family_id")
    private Integer familyId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "sort")
    private String sort;

    @TableField(exist = false)
    private List<Food> foods;
}
