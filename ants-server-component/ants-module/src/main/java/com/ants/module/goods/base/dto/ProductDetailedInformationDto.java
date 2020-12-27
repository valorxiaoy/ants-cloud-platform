package com.ants.module.goods.base.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 商品基础信息表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductDetailedInformationDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品简码(首字母缩写)
     */
    private String shortCode;
    /**
     * 商品规格(sku属性)
     */
    private String specs;
    /**
     * 商品保质期
     */
    private String qualityGuaranteePeriod;
    /**
     * 商品类型 0普通商品  1服务项目
     */
    private Integer type;
    /**
     * 配送方式
     */
    private Integer inDistribution;
    /**
     * 商品产地
     */
    private String goodsAddress;
    /**
     * 适用年龄
     */
    private String useAge;
    /**
     * 商品副标题
     */
    private String subheading;
    /**
     * 商品详情
     */
    private String detailed;
    /**
     * 商品介绍
     */
    private String introduce;
    /**
     * 商品详情图(富文本)
     */
    private String detailedFigure;
    /**
     * 类别
     */
    private Long categosId;
    /**
     * 品牌
     */
    private Long brandsId;
    /**
     * 系列
     */
    private Long tablesId;
    /**
     * 商品单位id
     */
    private Long companyId;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 商品等级id
     */
    private Long gradeId;
    /**
     * 下架上架 0下/1上
     */
    private Integer ifState;
    /**
     * 是否积分
     */
    private Integer ifIntegral;
    /**
     * 是否允许折扣
     */
    private Integer ifRebate;
    /**
     * 是否提成
     */
    private Integer ifCommission;
    /**
     * 是否前台修改价格
     */
    private Integer ifDeskPriceChange;
    /**
     * 是否允许促销
     */
    private Integer ifPromotion;
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
