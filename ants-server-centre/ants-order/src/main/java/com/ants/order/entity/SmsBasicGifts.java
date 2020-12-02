package com.ants.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 小米
 * @date 2020-08-06
 * 赠品营销
 */
@Data
@TableName("sms_basic_gifts")
public class SmsBasicGifts implements Serializable {


    /**
     *
     **/
    @TableField("store_id")
    private Integer storeId;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     *
     **/
    @TableField("name")
    private String name;


    /**
     * 状态  0上线
     * 1下线
     * <p>
     * 2已结束  3 已关闭
     **/
    @TableField("status")
    private Integer status;


    /**
     * 活动对象1全部用户2 会员级别
     **/
    @TableField("activi_user")
    private Integer activiUser;


    /**
     * 活动商品
     **/
    @TableField("activi_goods")
    private Integer activiGoods;


    /**
     * 1 首购礼 2 满 购礼 3 单品礼赠
     **/
    @TableField("big_type")
    private Integer bigType;


    /**
     * 首购礼 1第一单获取 2所有订单获取 ； 满购礼1消费金额 2购买件数
     **/
    @TableField("small_type")
    private Integer smallType;


    /**
     * 规则
     **/
    @TableField("rules")
    private String rules;


    /**
     * 部分商品列表
     **/
    @TableField("good_id")
    private String goodId;


    /**
     * 指定级别中的会员级别对应的id
     **/
    @TableField("user_level")
    private String userLevel;


    /**
     * 开始时间
     **/
    @TableField("start_time")
    private String startTime;


    /**
     * 结束时间
     **/
    @TableField("end_time")
    private String endTime;


    /**
     *
     **/
    @TableField("create_time")
    private String createTime;


    /**
     * 赠品
     **/
    @TableField("gift_ids")
    private String giftIds;


    /**
     *
     **/
    @TableField("note")
    private String note;


    /**
     * 平台 0全通用 1 线上 2线下
     **/
    @TableField("platform")
    private Integer platform;


    /**
     * 门店
     **/
    @TableField("store_children")
    private String storeChildren;


    /**
     * 指定商品中的商品名称对应的id
     **/
    @TableField("good_value")
    private String goodValue;


    /**
     * 指定分类中的分类名称对应的id
     **/
    @TableField("management_value")
    private String managementValue;
    /**
     * 修改按钮 是否禁用 0否 1是
     **/
    @TableField("update_index")
    private Integer updateIndex;
    /**
     * 可使用商品 0指定分类 1指定商品
     **/
    @TableField("use_good")
    private Integer useGood;
    /**
     * 是否上线  0下  1上
     **/
    @TableField("if_xian")
    private Integer ifXian;
    //商品详情
    private String goodDetailedInformation;
}
