package com.ants.module.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 子订单表
 *
 * @author Yueyang
 * @create 2020-11-16 7:46
 **/
@Data
public class OmsOrderItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 门店ID
     */
    private Integer storeId;

    /**
     * 订单编号
     */
    private String orderSn;

    private Integer productId;

    private String productPic;

    private String productName;

    private String productSubheading;

    private String productBrand;

    private String productSn;

    /**
     * 销售价格
     */
    private BigDecimal productPrice;

    /**
     * 购买数量
     */
    private Integer productQuantity;

    /**
     * 商品sku编号
     */
    private Long productSkuId;

    /**
     * 商品sku条码
     */
    private String productSkuCode;

    /**
     * 商品分类id
     */
    private Integer productCategoryId;

    /**
     * 商品的销售属性
     */
    private String sp1;

    private String sp2;

    private String sp3;

    /**
     * 商品促销名称
     */
    private String promotionName;

    /**
     * 商品促销分解金额
     */
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    private BigDecimal realAmount;

    /**
     * 秒杀活动id
     */
    private Integer giftIntegration;

    private Integer giftGrowth;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;

    /**
     * 原订单状态：2->待发货；3->已发货；
     */
    private Integer status;

    /**
     * 1 普通商品 2 赠品
     */
    private Integer type;

    private String storeName;

    private Integer isFenxiao;

    private Integer inviteMemberId;

    /**
     * 商品所需积分
     */
    private Integer productIntegral;
}