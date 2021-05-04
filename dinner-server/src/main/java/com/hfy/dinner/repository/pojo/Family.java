package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author hufayong
 * @date 2021/3/6 16:36
 */
@Data
@ToString
@TableName("family")
public class Family {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 家庭名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 家庭住址
     */
    @TableField(value = "location")
    private String location;
    /**
     * 就餐开始时间
     */
    @TableField(value = "begin_time")
    private Date beginTime;
    /**
     * 就餐结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;
    /**
     * 家庭人数
     */
    @TableField(value = "family_count")
    private Byte familyCount;
    /**
     * 一次可接收人数
     */
    @TableField(value = "receive_count")
    private Byte receiveCount;
    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private String statusT;
    /**
     * 销量
     */
    @TableField(value = "number")
    private Integer number;
    /**
     * 家庭简述
     */
    @TableField(value = "js")
    private String js;
    /**
     * 图片地址
     */
    @TableField(value = "img_location")
    private String imgLocation;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 评分
     */
    @TableField(value = "rate")
    private Float rating;
    /**
     * 单个价格
     */
    @TableField(value = "price")
    private Float price;
}
