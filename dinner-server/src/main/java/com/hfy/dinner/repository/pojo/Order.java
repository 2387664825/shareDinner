package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author hfy
 */
@Data
@TableName(value = "user_order")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "family_id")
    private Integer familyId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "price")
    private Float price;

    @TableField(value = "valid_time")
    private Date validTime;
}
