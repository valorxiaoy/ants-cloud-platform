package com.ants.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 小米
 * @date 2020-08-06
 * 小程序活动类型详情
 */
@Data
@TableName("applets_activity")
public class AppletsActivity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 状态
     **/
    @TableField("status")
    private Integer status;


    /**
     * 活动id
     **/
    @TableField("activity_id")
    private Integer activityId;


    /**
     * 活动json
     **/
    @TableField("activity_value")
    private String activityValue;


    /**
     * redis中的key
     **/
    @TableField("redis_key")
    private String redisKey;
    /**
     * 创建时间
     **/
    @TableField("create_time")
    private String createTime;

    /**
     * 活动类型
     */
    @TableField("activity_type")
    private String activityType = "";

    /**
     * 销量
     **/
    @TableField("sales_volume")
    private Integer salesVolume;

    /**
     * 剩余数量
     **/
    @TableField("remaining_quantity")
    private Integer remainingQuantity;

    /**
     * 发行数量
     **/
    @TableField("publish_count")
    private Integer publishCount;

    /**
     * 参与活动人数
     **/
    @TableField("activity_people")
    private Integer activityPeople;
    /*
     * 活动价格
     */
    @TableField("activity_price")
    private BigDecimal activityPrice;


}
