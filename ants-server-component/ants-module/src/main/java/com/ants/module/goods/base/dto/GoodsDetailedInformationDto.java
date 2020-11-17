package com.ants.module.goods.base.dto;

import com.ants.module.BaseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsDetailedInformationDto extends BaseDto {

    private Integer id;

    /**
     * 商品Id
     **/
    private String goodId;


    /**
     * 商品条码
     **/
    private String goodTiaoCode;


    /**
     * 商品名称
     **/
    private String goodName;


    /**
     * 商品简码
     **/
    private String goodJianCode;


    /**
     * 商品规格
     **/
    private String goodSpecs;


    /**
     * 保质期天数
     **/
    private String baozqDays;


    /**
     * 商品单位id
     **/
    private Integer goodCompanyId;

    /**
     * 供应商id
     **/
    private Integer goodSupplierId;

    /**
     * 商品等级id
     **/
    private Integer goodGradeId;


    /**
     * 商品进价
     **/
    private BigDecimal goodInprice;


    /**
     * 零售价
     **/
    private BigDecimal retailPrice;


    /**
     * 商品状态  正常/淘汰/暂停进货/停售   0/1/2/3
     **/
    private Integer goodState;


    /**
     * 毛利率
     **/
    private BigDecimal grossMargin;


    /**
     * 会员价金额
     **/
    private BigDecimal vipPrice;


    /**
     * 最低限价
     **/
    private BigDecimal lowerPrice;


    /**
     * 是否积分
     **/
    private Integer ifIntegral;


    /**
     * 是否允许折扣
     **/
    private Integer ifRebate;


    /**
     * 初始库存
     **/
    private Integer initialStock;


    /**
     * 是否提成
     **/
    private Integer ifCommission;


    /**
     * 是否前台修改价格
     **/
    private Integer ifDeskPriceChange;


    /**
     * 是否允许促销
     **/
    private Integer ifPromotion;


    /**
     * 会员百分比
     **/
    private String vipPercentage;


    /**
     * 商品类型 0普通商品  1服务项目
     **/
    private Integer goodType;


    /**
     * 项目类别id
     **/
    private Integer projectCategoryId;


    /**
     * 项目名称
     **/
    private String projectName;


    /**
     * 商品详情
     **/
    private String goodDetailed;


    /**
     * 商品介绍
     **/
    private String goodIntroduce;


    /**
     * 商品详情图
     **/
    private String goodDetailedFigure;


    /**
     * 配送方式
     **/
    private Integer inDistribution;


    /**
     * 开始时间
     **/
    private String startTime;


    /**
     * 结束时间
     **/
    private String endTime;


    /**
     * 规则id
     **/
    private Integer rulesId;


    /**
     * 创建时间
     **/
    private String createTime;


    /**
     * 批发价
     **/
    private BigDecimal wholesalePrice;


    /**
     * 提成价格
     **/
    private BigDecimal price;


    /**
     * 比例
     **/
    private Double proportion;


    /**
     * 是否有规则 0否  1是
     **/
    private Integer ifRules;


    /**
     * 类别路径
     **/
    private String path;


    /**
     * 下架上架 0下/1上
     **/
    private Integer ifState;


    /**
     * 导购分佣
     **/
    private Double guidePartServant;


    /**
     * 商品分佣
     **/
    private Double commodityPartServant;


    /**
     * 是否分佣 0否/1是
     **/
    private Integer part;

    /**
     * 品牌路径
     **/
    private String paths;
    /**
     * 商品产地
     */
    private String goodAddress;

    /**
     * 适用年龄
     */
    private String useAge;
    /**
     * 改后进货价
     */
    private BigDecimal goodInpriceSet;
    /**
     * 改后零售价
     */
    private BigDecimal retailPriceSet;
    /**
     * 改后最低价
     */
    private BigDecimal lowerPriceSet;
    /**
     * 改后会员价
     **/
    private BigDecimal vipPriceSet;
    /**
     * 改后批发价
     */
    private BigDecimal wholesalePriceSet;
    /**
     * 调价单号
     */
    private Integer priceAdjustmentId;

    /**
     * 是否为新品，0否，1是
     */
    private Integer newcommodity;

    /**
     * 是否为人气商品，0否，1是
     */
    private Integer popularitycommodity;

    /*
    商品副标题
     */
    private String subheading;

    /*
    类别
     */
    private int goodCategosId;

    /*
    品牌
     */
    private int goodBrandsId;

    /*
    系列
     */
    private int goodsTablesId;

    /**
     * 产品销量
     */
    private Integer salesVolume = 0;

    /**
     * 商品主图
     */
    private String goodsMainPicture;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 所需积分
     */
    private Integer integral;
}
