package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品调价记录表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductPriceAdjustmentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String billSn;
    /**
     * 状态 0未审核 1审核
     */
    private Integer state;
    /**
     * 制单人id
     */
    private Long userId;
    /**
     * 审核人id
     */
    private Long auditorUserId;
    /**
     * 审核时间
     */
    private String auditorTime;
    /**
     * 已废弃
     */
    private String makerTime;
    /**
     * 备注
     */
    private String note;
    /**
     * 已废弃
     */
    private String detaile;
    /**
     * 生效门店，已废弃
     */
    private Long auditstoreId;
    /**
     * 商品ID，已废弃
     */
    private Integer goodId;
    /**
     * 制单人类型，已废弃
     */
    private Integer userType;
    /**
     * 审核人类型，已废弃
     */
    private Integer auditorUserType;
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
