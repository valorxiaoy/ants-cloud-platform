package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaomi
 * @date 2020-06-05
 * 商品明细
 */
@Data
@TableName("good_detailed_information")
public class GoodsDetailedInformation implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 商品Id
     **/
    @TableField("good_id")
    private String goodId;


    /**
     * 商品条码
     **/
    @TableField("good_tiao_code")
    private String goodTiaoCode;


    /**
     * 商品名称
     **/
    @TableField("good_name")
    private String goodName;


    /**
     * 商品简码
     **/
    @TableField("good_jian_code")
    private String goodJianCode;


    /**
     * 商品规格
     **/
    @TableField("good_specs")
    private String goodSpecs;


    /**
     * 保质期天数
     **/
    @TableField("baozq_days")
    private String baozqDays;


    /**
     * 商品单位id
     **/
    @TableField("good_company_id")
    private Integer goodCompanyId;

    /**
     * 供应商id
     **/
    @TableField("good_supplier_id")
    private Integer goodSupplierId;

    /**
     * 商品等级id
     **/
    @TableField("good_grade_id")
    private Integer goodGradeId;


    /**
     * 商品进价
     **/
    @TableField("good_inprice")
    private BigDecimal goodInprice;


    /**
     * 零售价
     **/
    @TableField("retail_price")
    private BigDecimal retailPrice;


    /**
     * 商品状态  正常/淘汰/暂停进货/停售   0/1/2/3
     **/
    @TableField("good_state")
    private Integer goodState;


    /**
     * 毛利率
     **/
    @TableField("gross_margin")
    private BigDecimal grossMargin;


    /**
     * 会员价金额
     **/
    @TableField("vip_price")
    private BigDecimal vipPrice;


    /**
     * 最低限价
     **/
    @TableField("lower_price")
    private BigDecimal lowerPrice;


    /**
     * 是否积分
     **/
    @TableField("if_integral")
    private Integer ifIntegral;


    /**
     * 是否允许折扣
     **/
    @TableField("if_rebate")
    private Integer ifRebate;


    /**
     * 初始库存
     **/
    @TableField("initial_stock")
    private Integer initialStock;


    /**
     * 是否提成
     **/
    @TableField("if_commission")
    private Integer ifCommission;


    /**
     * 是否前台修改价格
     **/
    @TableField("if_desk_price_change")
    private Integer ifDeskPriceChange;


    /**
     * 是否允许促销
     **/
    @TableField("if_promotion")
    private Integer ifPromotion;


    /**
     * 会员百分比
     **/
    @TableField("vip_percentage")
    private String vipPercentage;


    /**
     * 商品类型 0普通商品  1服务项目
     **/
    @TableField("good_type")
    private Integer goodType;


    /**
     * 项目类别id
     **/
    @TableField("project_category_id")
    private Integer projectCategoryId;


    /**
     * 项目名称
     **/
    @TableField("project_name")
    private String projectName;


    /**
     * 商品详情
     **/
    @TableField("good_detailed")
    private String goodDetailed;


    /**
     * 商品介绍
     **/
    @TableField("good_introduce")
    private String goodIntroduce;


    /**
     * 商品详情图
     **/
    @TableField("good_detailed_figure")
    private String goodDetailedFigure;


    /**
     * 配送方式
     **/
    @TableField("in_distribution")
    private Integer inDistribution;


    /**
     * 开始时间
     **/
    @TableField("start_time")
    private String startTime;


    /**
     * 结束时间
     **/
    @TableField("end_time")
    private String endTime;


    /**
     * 规则id
     **/
    @TableField("rules_id")
    private Integer rulesId;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    private String createTime;


    /**
     * 批发价
     **/
    @TableField("wholesale_price")
    private BigDecimal wholesalePrice;


    /**
     * 提成价格
     **/
    @TableField("price")
    private BigDecimal price;


    /**
     * 比例
     **/
    @TableField("proportion")
    private Double proportion;


    /**
     * 是否有规则 0否  1是
     **/
    @TableField("if_rules")
    private Integer ifRules;


    /**
     * 类别路径
     **/
    @TableField("path")
    private String path;


    /**
     * 下架上架 0下/1上
     **/
    @TableField("if_state")
    private Integer ifState;


    /**
     * 导购分佣
     **/
    @TableField("guide_part_servant")
    private Double guidePartServant;


    /**
     * 商品分佣
     **/
    @TableField("commodity_part_servant")
    private Double commodityPartServant;


    /**
     * 是否分佣 0否/1是
     **/
    @TableField("part")
    private Integer part;

    /**
     * 品牌路径
     **/
    @TableField("paths")
    private String paths;
    /**
     * 商品产地
     */
    @TableField("good_address")
    private String goodAddress;

    /**
     * 适用年龄
     */
    @TableField("use_age")
    private String useAge;
    /**
     * 改后进货价
     */
    @TableField("good_inprice_set")
    private BigDecimal goodInpriceSet;
    /**
     * 改后零售价
     */
    @TableField("retail_price_set")
    private BigDecimal retailPriceSet;
    /**
     * 改后最低价
     */
    @TableField("lower_price_set")
    private BigDecimal lowerPriceSet;
    /**
     * 改后会员价
     **/
    @TableField("vip_price_set")
    private BigDecimal vipPriceSet;
    /**
     * 改后批发价
     */
    @TableField("wholesale_price_set")
    private BigDecimal wholesalePriceSet;
    /**
     * 调价单号
     */
    @TableField("price_adjustment_id")
    private Integer priceAdjustmentId;

    /**
     * 是否为新品，0否，1是
     */
    @TableField("new_commodity")
    private Integer newcommodity;

    /**
     * 是否为人气商品，0否，1是
     */
    @TableField("popularity_commodity")
    private Integer popularitycommodity;

    /*
    商品副标题
     */
    @TableField("subheading")
    private String subheading;

    /*
    类别
     */
    @TableField("good_categos_id")
    private int goodCategosId;

    /*
    品牌
     */
    @TableField("good_brands_id")
    private int goodBrandsId;

    /*
    系列
     */
    @TableField("goods_tables_id")
    private int goodsTablesId;

    /**
     * 产品销量
     */
    @TableField(exist = false)
    private Integer salesVolume = 0;

}
