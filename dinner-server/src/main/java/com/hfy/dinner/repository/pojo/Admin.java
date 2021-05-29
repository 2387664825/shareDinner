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
 * 管理员
 *
 * @author hfy
 */
@Data
@TableName(value = "admin")
public class Admin {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "password")
    private String password;

    @TableField(value = "name")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "login_time")
    private Date loginTime;

    @TableField(value = "type")
    private Integer type;
}
