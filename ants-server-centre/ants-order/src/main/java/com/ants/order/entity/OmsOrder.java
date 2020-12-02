package com.ants.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Data
@TableName("oms_order")
public class OmsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String openId;
    @TableField(exist = false)
    private String authCode;
    @TableField(exist = false)
    /**
     * 订单类型:ALIPAY，WECHAT，UNIONPAY(银联二维码），BESTPAY(翼支付)
     * 商家扫码用户支付时的支付类型，根据userAgent获取扫码用户手机信息;
     * 如果存在 micromessenger 为微信; 如果存在 alipayclient 则为支付宝; 否则，都不是
     */
    private String scanPayType;
    @TableField(exist = false)
    private String payCode;
    @TableField(exist = false)
    private BigDecimal blance;
    @TableField(exist = false)
    private String keyWord;
    @TableField(exist = false)
    private List<OmsOrderItem> orderItemList;
    @TableField(exist = false)
    private List<OmsOrderOperateHistory> historyList;
    /**
     * 可用的优惠券
     */
    @TableField(exist = false)
    private List<SmsCouponHistory> smsCoupons;

    /**
     * 可用的好礼相送活动
     */
    @TableField(exist = false)
    private List<SmsBasicGifts> smsBasicGifts;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer pid;
    /**
     * 会员id
     */
    @TableField("member_id")
    private Long memberId;

    @TableField("coupon_id")
    private Long couponId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 提交时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 用户帐号
     */
    @TableField("member_username")
    private String memberUsername;

    /**
     * 订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 应付金额（实际支付金额）
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    @TableField("freight_amount")
    private BigDecimal freightAmount;

    /**
     * 促销优化金额（促销价、满减、阶梯价）
     */
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     */
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 管理员后台调整订单使用的折扣金额
     */
    @TableField("discount_amount")
    private BigDecimal discountAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信；3->积分支付；4->现金; 5->网银
     */
    @TableField("pay_type")
    private Integer payType;

    /**
     * 订单来源：；1->小程序订单2 h5订单3PC订单  4 app
     * 订单来源：0->PC订单；1->app订单
     */
    @TableField("source_type")
    private Integer sourceType;

    @TableField("payment_status")
    private Integer paymentStatus;

    /**
     * 订单状态：0->待付款； 8->拼团进行中 ;1->付款中；2->待发货；3->待签收；4->已完成；5->退货 6->换货 7->已关闭
     */
    @TableField("status")
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 物流公司(配送方式)
     */
    @TableField("delivery_company")
    private String deliveryCompany;

    /**
     * 物流单号
     */
    @TableField("delivery_sn")
    private String deliverySn;

    /**
     * 自动确认时间（天）
     */
    @TableField("auto_confirm_day")
    private Integer autoConfirmDay;

    /**
     * 可以获得的积分
     */
    private double integration;

    /**
     * 可以活动的成长值
     */
    private Integer growth;

    /**
     * 活动信息
     */
    @TableField("promotion_info")
    private String promotionInfo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    @TableField("bill_type")
    private Integer billType;

    /**
     * 发票抬头
     */
    @TableField("bill_header")
    private String billHeader;

    /**
     * 发票内容
     */
    @TableField("bill_content")
    private String billContent;

    /**
     * 收票人电话
     */
    @TableField("bill_receiver_phone")
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    @TableField("bill_receiver_email")
    private String billReceiverEmail;

    /**
     * 收货人姓名
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 收货人电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;
    @TableField("receiver_id")
    private Long receiverId;


    /**
     * 收货人邮编
     */
    @TableField("receiver_post_code")
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    @TableField("receiver_province")
    private String receiverProvince;

    /**
     * 城市
     */
    @TableField("receiver_city")
    private String receiverCity;

    /**
     * 区
     */
    @TableField("receiver_region")
    private String receiverRegion;

    /**
     * 详细地址
     */
    @TableField("receiver_detail_address")
    private String receiverDetailAddress;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     */
    @TableField("confirm_status")
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    @TableField("delete_status")
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     */
    @TableField("use_integration")
    private Integer useIntegration;

    /**
     * 支付时间
     */
    @TableField("payment_time")
    private Date paymentTime;

    /**
     * 发货时间
     */
    @TableField("delivery_time")
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;

    /**
     * 评价时间
     */
    @TableField("comment_time")
    private Date commentTime;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    @TableField("prepay_id")
    private String prepayId;

    @TableField("supply_id")
    private Long supplyId;

    @TableField("goods_id")
    private Long goodsId;

    @TableField("goods_name")
    private String goodsName;

    @TableField("school_id")
    private Long schoolId;

    @TableField("store_id")
    private Integer storeId;

    @TableField("group_id")
    private Long groupId;
    @TableField("tax_type")
    private Integer taxType;    //	是否开发票 1=不发票 2=个人发票 3=公司发票
    @TableField("tax_content")
    private String taxContent;    //	0	发票内容
    @TableField("tax_code")
    private String taxCode;    //	税号
    @TableField("tax_title")
    private String taxTitle;    //	发票抬头
    @TableField("is_comment")
    private Integer isComment;    //是否评论，1未评论，2已评论

    @TableField("store_name")
    private String storeName;    //	税号
    @TableField("vip_amount")
    private BigDecimal vipAmount; // 会员价格
    @TableField("store_consume_id")
    private Integer storeConsumeId; // 消费门店

    @TableField("pay_integral")
    private Integer payIntegral; // 应付积分
    @TableField("is_delete")
    private Integer isDelete; // 是否删除 0否 1是

    @TableField("applets_activity_id")
    private Integer appletsActivityId; // 选择参与的活动

    @TableField("verification_code")
    private String verificationCode; // 核销码

}
