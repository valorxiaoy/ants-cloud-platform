package com.ants.module.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * 赠品营销活动
 *
 * @author Yueyang
 * @create 2020-11-16 7:49
 **/
@Data
public class SmsBasicGiftsDto implements Serializable {

    /**
     *
     **/
    private Integer storeId;

    private Long id;

    /**
     *
     **/
    private String name;

    /**
     * 状态  0上线
     * 1下线
     * <p>
     * 2已结束  3 已关闭
     **/
    private Integer status;

    /**
     * 活动对象1全部用户2 会员级别
     **/
    private Integer activiUser;

    /**
     * 活动商品
     **/
    private Integer activiGoods;

    /**
     * 1 首购礼 2 满 购礼 3 单品礼赠
     **/
    private Integer bigType;

    /**
     * 首购礼 1第一单获取 2所有订单获取 ； 满购礼1消费金额 2购买件数
     **/
    private Integer smallType;

    /**
     * 规则
     **/
    private String rules;

    /**
     * 部分商品列表
     **/
    private String goodId;

    /**
     * 指定级别中的会员级别对应的id
     **/
    private String userLevel;

    /**
     * 开始时间
     **/
    private String startTime;


    /**
     * 结束时间
     **/
    private String endTime;

    /**
     *
     **/
    private String createTime;

    /**
     * 赠品
     **/
    private String giftIds;

    /**
     *
     **/
    private String note;

    /**
     * 平台 0全通用 1 线上 2线下
     **/
    private Integer platform;

    /**
     * 门店
     **/
    private String storeChildren;

    /**
     * 指定商品中的商品名称对应的id
     **/
    private String goodValue;

    /**
     * 指定分类中的分类名称对应的id
     **/
    private String managementValue;

    /**
     * 修改按钮 是否禁用 0否 1是
     **/
    private Integer updateIndex;

    /**
     * 可使用商品 0指定分类 1指定商品
     **/
    private Integer useGood;

    /**
     * 是否上线  0下  1上
     **/
    private Integer ifXian;

    //商品详情
    private String goodDetailedInformation;
}