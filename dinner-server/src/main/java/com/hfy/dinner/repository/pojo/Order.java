package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "price")
    private Float price;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "valid_time")
    private Date validTime;
}
