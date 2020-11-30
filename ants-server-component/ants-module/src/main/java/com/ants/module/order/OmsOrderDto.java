package com.ants.module.order;

import com.ants.module.activity.SmsBasicGiftsDto;
import com.ants.module.activity.SmsCouponHistoryDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created Order Module
 *
 * @author Yueyang
 * @create 2020-11-16 7:34
 **/
@Data
public class OmsOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openId;

    private String authCode;

    /**
     * 订单类型:ALIPAY，WECHAT，UNIONPAY(银联二维码），BESTPAY(翼支付)
     * 商家扫码用户支付时的支付类型，根据userAgent获取扫码用户手机信息;
     * 如果存在 micromessenger 为微信;
     * 如果存在 alipayclient 则为支付宝;
     * 否则，都不是
     */
    private String scanPayType;

    private String payCode;

    private BigDecimal blance;

    private String keyWord;

    private List<OmsOrderItemDto> orderItemList;

    /**
     * 可用的优惠券
     */
    private List<SmsCouponHistoryDto> smsCoupons;

    /**
     * 可用的好礼相送活动
     */
    private List<SmsBasicGiftsDto> smsBasicGifts;

    /**
     * 订单id
     */
    private Long id;

    private Integer pid;
    /**
     * 会员id
     */
    private Long memberId;

    private Long couponId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 提交时间
     */
    private String createTime;

    /**
     * 用户帐号
     */
    private String memberUsername;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额（实际支付金额）
     */
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    private BigDecimal freightAmount;

    /**
     * 促销优化金额（促销价、满减、阶梯价）
     */
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     */
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 管理员后台调整订单使用的折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信；3->积分支付；4->现金; 5->网银
     */
    private Integer payType;

    /**
     * 订单来源：；1->小程序订单2 h5订单3PC订单  4 app
     */
    private Integer sourceType;

    private Integer paymentStatus;

    /**
     * 订单状态：0->待付款； 8->拼团进行中 ;1->付款中；2->待发货；3->待签收；4->已完成；5->退货 6->换货 7->已关闭
     */
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
     */
    private Integer orderType;

    /**
     * 物流公司(配送方式)
     */
    private String deliveryCompany;

    /**
     * 物流单号
     */
    private String deliverySn;

    /**
     * 自动确认时间（天）
     */
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
    private String promotionInfo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private Integer billType;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 收票人电话
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    private Long receiverId;


    /**
     * 收货人邮编
     */
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    private String receiverProvince;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 区
     */
    private String receiverRegion;

    /**
     * 详细地址
     */
    private String receiverDetailAddress;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     */
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     */
    private Integer useIntegration;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    private Date receiveTime;

    /**
     * 评价时间
     */
    private Date commentTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    private String prepayId;

    private Long supplyId;

    private Long goodsId;

    private String goodsName;

    private Long schoolId;

    private Integer storeId;

    private Long groupId;

    /**
     * 是否开发票 1=不发票 2=个人发票 3=公司发票
     */
    private Integer taxType;

    /**
     * 0	发票内容
     */
    private String taxContent;

    /**
     * 税号
     */
    private String taxCode;

    /**
     * 发票抬头
     */
    private String taxTitle;

    /**
     * 是否评论，1未评论，2已评论
     */
    private Integer isComment;

    /**
     * 税号
     */
    private String storeName;

    /**
     * 会员价格
     */
    private BigDecimal vipAmount;

    /**
     * 消费门店
     */
    private Integer storeConsumeId;

    /**
     * 应付积分
     */
    private Integer payIntegral;

    /**
     * 是否删除 0否 1是
     */
    private Integer isDelete;

    /**
     * 选择参与的活动
     */
    private Integer appletsActivityId;

    /**
     * 核销码
     */
    private String verificationCode;


}