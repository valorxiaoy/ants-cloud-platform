package com.ants.module.goods.base.dto;

import com.ants.module.BaseDto;
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
public class ProductPriceAdjustmentItemDto extends BaseDto implements Serializable {
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

}
