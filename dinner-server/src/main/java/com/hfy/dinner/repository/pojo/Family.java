package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author hufayong
 * @date 2021/3/6 16:36
 */
@Data
@ToString
@TableName("family")
public class Family implements Comparable<Family> {

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
     * 家庭住址
     */
    @TableField(value = "location_code")
    private Integer locationCode;
    /**
     * 就餐开始时间
     */
    @TableField(value = "begin_time")
    private String beginTime;
    /**
     * 就餐结束时间
     */
    @TableField(value = "end_time")
    private String endTime;
    /**
     * 家庭人数
     */
    @TableField(value = "family_count")
    private Integer familyCount;
    /**
     * 一次可接收人数
     */
    @TableField(value = "receive_count")
    private Integer receiveCount;
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
     * 图片地址
     */
    @TableField(value = "jkz")
    private String jkz;
    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    @TableField(exist = false)
    private Integer yy;

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

    /**
     * 纬度
     */
    @TableField(value = "wz_x")
    private Double wzX;
    /**
     * 经度
     */
    @TableField(value = "wz_y")
    private Double wzy;

    @TableField(value = "because")
    private String because;
    /**
     * 当前距离
     */
    @TableField(exist = false)
    private Double jl;

    @TableField(exist = false)
    private Integer userId;
    @Override
    public int compareTo(Family o) {
        if (this.jl < o.jl) {
            return -1;
        } else if (this.jl == o.jl) {
            return 0;
        } else {
            return 1;
        }
    }
}
