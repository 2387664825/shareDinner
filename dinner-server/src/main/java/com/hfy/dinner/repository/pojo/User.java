package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hufayong
 * @date 2021/3/6 21:59
 */
@Setter
@Getter
@TableName(value = "user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "open_id")
    private String openId;

    @TableField(value = "family_id")
    private Integer familyId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "id_card")
    private String idCard;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "birthday")
    private Date birthday;

    @TableField(value = "credit")
    private Integer credit;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "city")
    private String city;

    @TableField(value = "picture")
    private String picture;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "login_time")
    private Date loginTime;
}
