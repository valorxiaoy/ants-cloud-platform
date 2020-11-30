package com.ants.activity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 活动匹配规则模型
 *
 * @author Yueyang
 * @create 2020-11-28 13:09
 **/
@Data
public class MatchingRulesDto {
    /**
     * 活动类型
     * 1 首购赠礼
     * 2 满购赠礼
     * 3 单品赠礼
     */

    /**
     * 订单来源
     */
    private Integer sourceType;

    /**
     * 会员级别
     */
    private Long memberLevelId;

    /**
     * 所含分类ID
     */
    private List<Integer> categoryList;

    /**
     * 所含商品ID
     */
    private List<Long> goodsIds;

    /**
     * 订单所含商品各分类消费金额
     */
    private Map<Integer, BigDecimal> productCategoryAmountMap;

    /**
     * 订单所含商品各分类购买件数
     */
    private Map<Integer, Integer> productCategoryNumMap;

    /**
     * 订单所含各商品消费金额
     */
    private Map<Long, BigDecimal> productAmountMap;

    /**
     * 订单所含各商品购买件数
     */
    private Map<Long, Integer> productNumMap;
}