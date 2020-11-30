package com.ants.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 优惠券使用、领取历史表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("sms_coupon_history")
public class SmsCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("store_id")
    private Integer storeId;

    @TableField("coupon_id")
    private Long couponId;
    /**
     * 使用门槛；0表示无门槛
     */
    @TableField("min_point")
    private BigDecimal minPoint;

    @TableField("member_id")
    private Long memberId;
    /**
     * 领取人名称
     */
    @TableField("coupon_code")
    private String couponCode;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 领取人昵称
     */
    @TableField("member_nickname")
    private String memberNickname;

    /**
     * 获取类型：0->后台赠送；1->主动获取
     */
    @TableField("get_type")
    private Integer getType;

    @TableField("create_time")
    private String createTime;

    /**
     * 使用状态：0->未使用；1->已使用；2->已过期
     */
    @TableField("use_status")
    private Integer useStatus;

    /**
     * 使用时间
     */
    @TableField("use_time")
    private String useTime;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单号码
     */
    @TableField("order_sn")
    private String orderSn;

    @TableField("start_time")
    private String startTime;

    @TableField("end_time")
    private String endTime;

    private String note;
    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     */
    @TableField("type")
    private Integer type;
    /**
     * 优惠券名称
     */
    @TableField("name")
    private String name;
    /**
     * 使用平台：0->全部；1->线上活动；2->线下活动
     */
    @TableField("platform")
    private Integer platform;
    /**
     * 每人限领张数
     */
    @TableField("per_limit")
    private Integer perLimit;

    /**
     * 总发行量
     */
    @TableField("publish_count")
    private Integer publishCount;
    /**
     * 使用类型：0->全场通用；1->指定分类；2->指定商品
     */
    @TableField("use_type")
    private Integer useType;
    /**
     * 订单价格
     */
    @TableField("order_price")
    private BigDecimal orderPrice;

    /**
     * 商品ID
     */
    @TableField("good_id")
    private String goodId;
    /**
     * 类别id
     */
    @TableField("category_id")
    private String categoryId;


}
