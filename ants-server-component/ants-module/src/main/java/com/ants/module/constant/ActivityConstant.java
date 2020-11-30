package com.ants.module.constant;

/**
 * 活动常量类
 *
 * @author Yueyang
 * @create 2020-11-30 11:11
 **/
public class ActivityConstant {

    /**
     * 订单来源: 0->PC 1->小程序
     */
    public final static Integer SOURCE_TYPE_CASHIER = 0;
    public final static Integer SOURCE_TYPE_MINI_PROGRAMS = 1;

    /**
     * 平台类型 0->全部；1->线上活动；2->线下活动
     */
    public final static Integer PLAT_FORM_ALL = 0;
    public final static Integer PLAT_FORM_ONLINE = 1;
    public final static Integer PLAT_FORM_OFFLINE = 2;


    /**
     * 拼团活动类型
     */
    public final static String ACTIVITY_TYPE_GROUP = "1";
    /**
     * 秒杀活动类型
     */
    public final static String ACTIVITY_TYPE_SECKILL = "2";
    /**
     * 优惠券活动类型
     */
    public final static String ACTIVITY_TYPE_COUPON = "3";
    /**
     * 满减折扣活动类型
     */
    public final static String ACTIVITY_TYPE_BASICMARJING = "4";
    /**
     * 赠品活动类型
     */
    public final static String ACTIVITY_TYPE_BASICGIFTS = "5";
    /**
     * 引流活动类型
     */
    public final static String ACTIVITY_TYPE_GTOUPACTIVITY = "6";
    /**
     * 广告列表类型
     */
    public final static String ACTIVITY_TYPE_HOMEADVERTISE = "7";
}