package com.ants.order.pay.fuyou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单中所包含的商品
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Data
@TableName("oms_order_item")
public class OmsOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("store_id")
    private Integer storeId;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    @TableField("product_id")
    private Integer productId;

    @TableField("product_pic")
    private String productPic;

    @TableField("product_name")
    private String productName;
    @TableField("product_subheading")
    private String productSubheading;
    @TableField("product_brand")
    private String productBrand;

    @TableField("product_sn")
    private String productSn;

    /**
     * 销售价格
     */
    @TableField("product_price")
    private BigDecimal productPrice;

    /**
     * 购买数量
     */
    @TableField("product_quantity")
    private Integer productQuantity;

    /**
     * 商品sku编号
     */
    @TableField("product_sku_id")
    private Long productSkuId;

    /**
     * 商品sku条码
     */
    @TableField("product_sku_code")
    private String productSkuCode;

    /**
     * 商品分类id
     */
    @TableField("product_category_id")
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
    @TableField("promotion_name")
    private String promotionName;

    /**
     * 商品促销分解金额
     */
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    @TableField("real_amount")
    private BigDecimal realAmount;

    /**
     * 秒杀活动id
     */
    @TableField("gift_integration")
    private Integer giftIntegration;

    @TableField("gift_growth")
    private Integer giftGrowth;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    @TableField("product_attr")
    private String productAttr;

    /**
     * 原订单状态：2->待发货；3->已发货；
     */
    private Integer status;

    /**
     * 1 普通商品 2 赠品
     */
    private Integer type;

    @TableField("store_name")
    private String storeName;

    @TableField("is_fenxiao")
    private Integer isFenxiao;

    @TableField("invite_memberId")
    private Integer inviteMemberId;

    /**
     * 商品所需积分
     */
    @TableField("product_integral")
    private Integer productIntegral;
}
