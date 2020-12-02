package com.ants.pay.fy.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * 富有支付-常量类
 *
 * @author Yueyang
 * @create 2020-11-09 19:30
 **/
public class FyPayConstant {
    /**
     * 【推荐】集合初始化时，指定集合初始值大小
     * 说明：HashMap使用HashMap(int initialCapacity)初始化，
     * 正例：initialCapacity=(需要存储的元素个数/负载因子)+1。注意负载因子(即loaderfactor)默认为0.75，
     * 如果暂时无法确定初始值大小，请设置为16(即默认值)。
     * 反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表，严重影响性能。
     */
    public final static Integer HASH_MAP_DEFAULT = 16;
    public final static Integer HASH_MAP_ZERO = 0;
    /**
     * 小蚂蚁商户私钥
     */
    @Value("ANTS.INS_PRIVATE_KEY")
    public static String INS_PRIVATE_KEY;
    /**
     * 小蚂蚁商户公钥
     */
    @Value("ANTS.INS_PUBLIC_KEY")
    public static String INS_PUBLIC_KEY;
    /**
     * 富有公钥
     */
    @Value("FY.FY_PUBLIC_KEY")
    public static String FY_PUBLIC_KEY;
    /**
     * 富有统一下单地址
     */
    @Value("FY.ORDER_CREATE_URL")
    public static String ORDER_CREATE_URL;
    /**
     * 小蚂蚁统一支付结果回调地址
     */
    @Value("ANTS.ORDER_NOTIFY_URL")
    public static String ORDER_NOTIFY_URL;

}