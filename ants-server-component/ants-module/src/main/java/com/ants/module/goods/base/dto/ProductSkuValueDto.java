package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品sku值表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductSkuValueDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * sku key编码
     */
    private String skuKeyCode;
    /**
     * 值编码
     */
    private String skuValueCode;
    /**
     * 属性值
     */
    private String attributeValue;
    /**
     * 值排序
     */
    private Integer valueSort;
    /**
     * 所属门店ID
     */
    private Long storeId;
    /**
     * 是否禁用
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
