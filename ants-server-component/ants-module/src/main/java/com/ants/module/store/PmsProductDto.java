package com.ants.module.store;

import com.ants.tools.utils.TimeSecound;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 *
 * @author Yueyang
 * @create 2020-11-16 8:06
 **/
public class PmsProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long memberId;

    private Long brandId;

    private Long productCategoryId;

    private Long feightTemplateId;

    private Long productAttributeCategoryId;

    private String name;

    private String pic;
    /**
     * 1出售、2义卖、3免费
     */
    private Integer type;

    /**
     * 1普通 2拍卖
     */
    private Integer isPaiMai;

    private Date expireTime;

    /**
     * 货号
     */
    private String productSn;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;

    /**
     * 上架状态：0->下架；1->上架
     */
    private Integer publishStatus;

    /**
     * 新品状态:0->不是新品；1->新品
     */
    private Integer newStatus;

    /**
     * 推荐状态；0->不推荐；1->推荐
     */
    private Integer recommandStatus;

    /**
     * 审核状态：0->未审核；1->审核通过
     */
    private Integer verifyStatus;

    private Integer isFenxiao;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 销量
     */
    private Integer sale;

    private BigDecimal price;

    /**
     * 促销价格
     */
    private BigDecimal promotionPrice;

    /**
     * 收藏
     */
    private Integer giftGrowth;

    /**
     * 点赞
     */
    private Integer giftPoint;

    /**
     * 限制使用的积分数
     */
    private Integer usePointLimit;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 市场价
     */
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 库存预警值
     */
    private Integer lowStock;

    /**
     * 单位
     */
    private String unit;

    /**
     * 商品重量，默认为克
     */
    private BigDecimal weight;

    /**
     * 是否为预告商品：0->不是；1->是
     */
    private Integer previewStatus;

    /**
     * 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
     */
    private String serviceIds;

    private String keywords;

    private String note;

    /**
     * 画册图片，连产品图片限制为5张，以逗号分割
     */
    private String albumPics;

    private String detailTitle;

    private String detailDesc;

    /**
     * 产品详情网页内容
     */
    private String detailHtml;

    /**
     * 移动端网页详情
     */
    private String detailMobileHtml;

    /**
     * 促销开始时间
     */
    private Date promotionStartTime;

    /**
     * 促销结束时间
     */
    private Date promotionEndTime;

    /**
     * 活动限购数量
     */
    private Integer promotionPerLimit;

    /**
     * 促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购
     */
    private Integer promotionType;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 运费
     */
    private BigDecimal transfee;

    /**
     * 商品分类名称
     */
    private String productCategoryName;

    private Long supplyId;

    private Long areaId;

    private Date createTime;

    private Long schoolId;

    private Integer hit;

    private String keyword;

    private String areaName;

    private String schoolName;

    private String storeName;

    /**
     * 1 學校 2 區域
     */
    private int qsType;

    private BigDecimal fenxiaoPrice;

    private TimeSecound timeSecound;
}