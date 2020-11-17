package com.ants.module.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠卷使用、领取历史表
 *
 * @author Yueyang
 * @create 2020-11-16 7:48
 **/
@Data
public class SmsCouponHistoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long couponId;

    /**
     * 使用门槛；0表示无门槛
     */
    private BigDecimal minPoint;

    private Long memberId;

    /**
     * 领取人名称
     */
    private String couponCode;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 领取人昵称
     */
    private String memberNickname;

    /**
     * 获取类型：0->后台赠送；1->主动获取
     */
    private Integer getType;

    private String createTime;

    /**
     * 使用状态：0->未使用；1->已使用；2->已过期
     */
    private Integer useStatus;

    /**
     * 使用时间
     */
    private String useTime;

    /**
     * 订单编号
     */
    private Long orderId;

    /**
     * 订单号码
     */
    private String orderSn;

    private String startTime;

    private String endTime;

    private String note;

    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     */
    private Integer type;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 使用平台：0->全部；1->线上活动；2->线下活动
     */
    private Integer platform;

    /**
     * 每人限领张数
     */
    private Integer perLimit;

    /**
     * 总发行量
     */
    private Integer publishCount;

    /**
     * 使用类型：0->全场通用；1->指定分类；2->指定商品
     */
    private Integer useType;
    /**
     * 订单价格
     */
    private BigDecimal orderPrice;

    /**
     * 商品ID
     */
    private String goodId;
    /**
     * 类别id
     */
    private String categoryId;
}