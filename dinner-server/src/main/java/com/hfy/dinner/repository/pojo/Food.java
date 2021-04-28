package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hufayong
 * @date 2021/3/6 22:08
 */
@Data
@TableName(value = "food")
public class Food {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应家庭的id
     */
    @TableField(value = "family_id")
    private Integer familyId;

    /**
     * 菜名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 价格
     */
    @TableField(value = "price")
    private float price;

    /**
     * 份量
     */
    @TableField(value = "size")
    private Integer size;
    /**
     * 图片
     */
    @TableField(value = "picture")
    private String picture;
    /**
     * 简述
     */
    @TableField(value = "js")
    private String js;

    /**
     * 类别
     */
    @TableField(value = "category_id")
    private Integer categoryId;
}
