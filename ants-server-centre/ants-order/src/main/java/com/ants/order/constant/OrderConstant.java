package com.ants.order.constant;

/**
 * 富有支付-常量类
 *
 * @author Yueyang
 * @create 2020-11-09 19:30
 **/
public class OrderConstant {
    /**
     * 【推荐】集合初始化时，指定集合初始值大小
     * 说明：HashMap使用HashMap(int initialCapacity)初始化，
     * 正例：initialCapacity=(需要存储的元素个数/负载因子)+1。注意负载因子(即loaderfactor)默认为0.75，
     * 如果暂时无法确定初始值大小，请设置为16(即默认值)。
     * 反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表，严重影响性能。
     */
    public final static Integer HASH_MAP_DEFAULT = 16;
    public final static Integer HASH_MAP_ZERO = 0;

    /** -------------------订单状态--------------------- */

    /**
     * 待付款
     */
    public static final Integer ORDER_STATUS_TO_BE_PAID = 0;
    /**
     * 付款中
     **/
    public static final Integer ORDER_STATUS_PAYMENT_IN_PROGRESS = 1;
    /**
     * 待发货
     */
    public static final Integer ORDER_STATUS_TO_BE_DELIVERED = 2;
    /**
     * 待签收
     */
    public static final Integer ORDER_STATUS_TO_BE_SIGNED = 3;
    /**
     * 已完成
     */
    public static final Integer ORDER_STATUS_COMPLETED = 4;
    /**
     * 退货
     */
    public static final Integer ORDER_STATUS_RETURN_GOODS = 5;
    /**
     * 换货
     */
    public static final Integer ORDER_STATUS_EXCHANGE_GOODS = 6;
    /**
     * 已关闭
     */
    public static final Integer ORDER_STATUS_CLOSED = 7;
    /**
     * 待拼团
     */
    public static final Integer ORDER_STATUS_MAKE_GROUP = 8;


    /** -------------------支付状态--------------------- */
    /**
     * 未支付
     */
    public static final String PAY_UNPAID = "0";
    /**
     * 支付宝支付
     */
    public static final String PAY_ALIPAY = "1";
    /**
     * 微信支付
     */
    public static final String PAY_WECHAT = "2";


    /** -------------------订单来源--------------------- */
    /**
     * PC订单
     */
    public static final String PAY_PC = "0";
    /**
     * 小程序
     */
    public static final String PAY_APPLETS = "1";


    /** -------------------订单类型--------------------- */
    /**
     * 正常订单
     */
    public static final String ORDER_TYPE_NORMAL = "0";
    /**
     * 秒杀订单
     */
    public static final String ORDER_TYPE_FLASH_SALE = "1";
    /**
     * 门店自取订单
     */
    public static final String ORDER_TYPE_STORE = "2";
    /**
     * 拼团订单
     */
    public static final String ORDER_TYPE_MAKE_GROUP = "3";
    /**
     * 团购订单
     */
    public static final String ORDER_TYPE_GROUP_BUY = "4";
    /**
     * 积分订单
     */
    public static final String ORDER_TYPE_INTEGRAL = "5";

    /** -------------------发票类型--------------------- */
    /**
     * 不开发票
     */
    public static final String BILL_TYPE_NO_BILL = "0";
    /**
     * 电子发票
     */
    public static final String BILL_TYPE_ELECTRON = "1";
    /**
     * 纸质发票
     */
    public static final String BILL_TYPE_PAPER = "2";

    /** -------------------收获状态--------------------- */
    /**
     * 未收货
     */
    public static final String HARVEST_STATUS_NO = "0";
    /**
     * 已收获
     */
    public static final String HARVEST_STATUS_YES = "1";

    /** -------------------是否开票--------------------- */
    /**
     * 不开票
     */
    public static final String TAX_TYPE_NO = "0";
    /**
     * 个人发票
     */
    public static final String TAX_TYPE_PERSONAL = "1";
    /**
     * 公司发票
     */
    public static final String TAX_TYPE_COMPANY = "2";

}