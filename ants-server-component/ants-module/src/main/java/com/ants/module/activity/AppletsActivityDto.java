package com.ants.module.activity;

import lombok.Data;

@Data
public class AppletsActivityDto {

    /**
     * 活动编号
     */
    private String activityId;

    /**
     * 活动图片
     */
    private String activityPicture;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动目标/商品副标题
     */
    private String activityTarget;

    /**
     * 活动价格
     */
    private String activityPrice;

    /**
     * 活动销量
     */
    private String activitySalesVolume = "0";

    /**
     * 活动内容
     */
    private String activityValue;

    /**
     * 商品详情
     */
    private String goodDetailedInformation;

    /**
     * 活动开始时间
     */
    private String activityStartTime;

    /**
     * 活动结束时间
     */
    private String activityEndTime;

    /**
     * 商品原价
     **/
    private String goodPrice;

    /**
     * RedisKey
     */
    private String redisKey;

    /**
     * 备注
     */
    private String note;
}
