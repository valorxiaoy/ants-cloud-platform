package com.ants.base.goods.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author 小米
 * @date 2020-11-20
 * 商品价格调整明细表
 */
@Data
@TableName("good_price_adjustment_item")
public class GoodsPriceAdjustmentItem implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 调价单号id
     **/
    @TableField("order_id")
    private String orderId;


    /**
     * 商品id
     **/
    @TableField("good_id")
    private Integer goodId;


    /**
     * 零售价
     **/
    @TableField("retail_price")
    private BigDecimal retailPrice;


    /**
     * 批发价
     **/
    @TableField("wholesale_price")
    private BigDecimal wholesalePrice;


    /**
     * 最低限价
     **/
    @TableField("lower_price")
    private BigDecimal lowerPrice;


    /**
     * 删除状态：0->未删除；1->已删除
     **/
    @TableField("delete_status")
    private Integer deleteStatus;


}
