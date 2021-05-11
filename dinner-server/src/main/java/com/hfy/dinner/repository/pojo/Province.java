package com.hfy.dinner.repository.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hfy
 * @date 2021/5/11 23:27
 */
@Data
@TableName(value = "province")
public class Province {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "code")
    private Long code;

    @TableField(value = "name")
    private String name;

    @TableField(value = "province")
    private String province;

    @TableField(value = "city")
    private String city;

    @TableField(value = "area")
    private String area;

    @TableField(value = "town")
    private String town;
}
