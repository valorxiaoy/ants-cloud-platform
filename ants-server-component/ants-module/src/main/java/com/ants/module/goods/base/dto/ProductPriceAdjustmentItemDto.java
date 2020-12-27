package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品调价记录详情表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductPriceAdjustmentItemDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
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
    private String createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新人
     */
    private Long updateId;

}
