package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 商品调价记录详情表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
@TableName("goods_price_adjustment_item")
public class GoodsPriceAdjustmentItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 订单编号
     */
    private Long orderSn;
    /**
     * 商品id
     */
    private Long goodsCode;
    /**
     * 零售价
     */
    private BigDecimal retailPrice;
    /**
     * 批发价
     */
    private BigDecimal wholesalePrice;
    /**
     * 最低限价
     */
    private BigDecimal lowerPrice;
    /**
     * 所属门店ID
     */
    private Long storeId;
    /**
     * 是否禁用：0->未禁用；1->已禁用
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Long updateId;

}
