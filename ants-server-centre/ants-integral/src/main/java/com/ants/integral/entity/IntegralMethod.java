package com.ants.integral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘智
 * @date 2020-07-30
 * 积分方式
 */
@Data
@TableName("ums_integral_method")
public class IntegralMethod implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 方式名称
     **/
    @TableField("method")
    private String method;

    /**
     * 门店id
     */
    @TableField("store_id")
    private Integer storeId;


}
