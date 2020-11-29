package com.ants.activity.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ants.activity.dto.MatchingRulesDto;
import com.ants.dubbo.api.base.member.IMemberService;
import com.ants.dubbo.api.service.activity.IMatchingSmsBasicGiftsService;
import com.ants.dubbo.api.service.activity.ISmsBasicGiftsService;
import com.ants.module.member.UmsMemberDto;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.OmsOrderItemDto;
import com.ants.module.order.SmsBasicGiftsDto;
import com.ants.tools.exception.BusinessException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据订单匹配赠品营销活动
 *
 * @author Yueyang
 * @create 2020-11-28 13:07
 **/
@DubboService
public class MatchingSmsBasicGiftsServiceImpl implements IMatchingSmsBasicGiftsService {

    @DubboReference
    private IMemberService memberService;

    @DubboReference
    private ISmsBasicGiftsService smsBasicGiftsService;

    /**
     * 根据订单数据，查询符合规则的赠品营销活动
     *
     * @param omsOrderDto 订单数据
     * @return 匹配到的赠品营销活动
     */
    @Override
    public List<SmsBasicGiftsDto> searchMatchingBasicGiftsByOrder(OmsOrderDto omsOrderDto) {
        if (omsOrderDto == null || omsOrderDto.getOrderItemList() == null || omsOrderDto.getOrderItemList().size() == 0) {
            throw new BusinessException(String.format("订单数据不合法，参数OmsOrderDto: %s", JSONObject.toJSONString(omsOrderDto)));
        }
        // 门店ID
        Integer storeId = omsOrderDto.getStoreId();
        // 订单编号
        String orderSn = omsOrderDto.getOrderSn();
        // 订单来源：0->PC订单；1->app订单
        Integer sourceType = omsOrderDto.getSourceType();
        // 子订单列表
        List<OmsOrderItemDto> omsOrderItems = omsOrderDto.getOrderItemList();
        // 根据门店ID获取所有赠品营销活动
        List<SmsBasicGiftsDto> smsBasicGiftsDtoList = searchSmsBasicGiftsByStoreId(storeId, sourceType);
        // 提取匹配规则
        MatchingRulesDto matchingRules = getMatchingRules(omsOrderDto);
        // 匹配赠品营销活动
        List<SmsBasicGiftsDto> smsBasicGiftsDto = getSmsBasicGiftsDto(matchingRules, smsBasicGiftsDtoList);
        // 匹配活动规则
        List<SmsBasicGiftsDto> results = getSmsBasicGiftsDto(matchingRules, smsBasicGiftsDtoList);
        return results;
    }

    /**
     * 查询线上活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    private List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreId(Integer storeId, Integer sourceType) {
        List<SmsBasicGiftsDto> smsBasicGiftsDtoList = null;
        if ("0".equals(sourceType)) {
            smsBasicGiftsDtoList = smsBasicGiftsService.searchSmsBasicGiftsByStoreIdOnline(storeId);
        } else if ("1".equals(sourceType)) {
            smsBasicGiftsDtoList = smsBasicGiftsService.searchSmsBasicGiftsByStoreIdOffline(storeId);
        }
        if (smsBasicGiftsDtoList == null || smsBasicGiftsDtoList.size() == 0) {
            throw new BusinessException(String.format("赠品营销活动获取失败，参数storeId: %s", storeId));
        }
        return smsBasicGiftsDtoList;
    }

    /**
     * 提取订单中待匹配规则
     *
     * @return 待匹配规则
     */
    private MatchingRulesDto getMatchingRules(OmsOrderDto omsOrderDto) {
        if (omsOrderDto == null || omsOrderDto.getOrderItemList() == null || omsOrderDto.getOrderItemList().size() == 0) {
            throw new BusinessException(String.format("订单数据不合法，参数OmsOrderDto: %s", JSONObject.toJSONString(omsOrderDto)));
        }
        // 子订单列表
        List<OmsOrderItemDto> orderItemList = omsOrderDto.getOrderItemList();
        /** 开始提取 **/
        // 订单来源
        Integer sourceType = omsOrderDto.getSourceType();
        // 会员级别
        Long memberId = omsOrderDto.getMemberId();
        UmsMemberDto umsMemberDto = memberService.searchUmsMember(memberId.intValue());
        Long memberLevelId = umsMemberDto.getMemberLevelId();
        // 订单所含分类
        List<Integer> categoryList = orderItemList.stream().map(bean -> bean.getProductCategoryId()).collect(Collectors.toList());
        // 订单所含各分类中的商品信息
        Map<Integer, List<OmsOrderItemDto>> collect = orderItemList.stream().collect(Collectors.groupingBy(OmsOrderItemDto::getProductCategoryId));
        // 订单所含商品ID
        List<Long> goodsIds = orderItemList.stream().map(bean -> bean.getId()).collect(Collectors.toList());
        // 订单所含商品各分类消费金额
        Map<Integer, BigDecimal> productCategoryAmountMap = new HashMap<>();
        collect.forEach((key, value) -> {
            BigDecimal amount = value.stream().map(OmsOrderItemDto::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            productCategoryAmountMap.put(key, amount);
        });
        // 订单所含商品各分类购买件数
        Map<Integer, Integer> productCategoryNumMap = new HashMap<>();
        collect.forEach((key, value) -> {
            Integer productQuantity = value.stream().mapToInt(OmsOrderItemDto::getProductQuantity).sum();
            productCategoryNumMap.put(key, productQuantity);
        });
        // 订单所含商品
        Map<Long, List<OmsOrderItemDto>> goodsCollect = orderItemList.stream().collect(Collectors.groupingBy(OmsOrderItemDto::getId));
        // 订单所含各商品消费金额
        Map<Long, BigDecimal> productAmountMap = new HashMap<>();
        goodsCollect.forEach((key, value) -> {
            value.stream().forEach(bean -> {
                BigDecimal productPrice = bean.getProductPrice();
                Integer productQuantity = bean.getProductQuantity();
                BigDecimal amount = productPrice.multiply(new BigDecimal(productQuantity));
                productAmountMap.put(key, amount);
            });
        });
        // 订单所含各商品购买件数
        Map<Long, Integer> productNumMap = new HashMap<>();
        goodsCollect.forEach((key, value) -> {
            value.stream().forEach(bean -> {
                Integer productQuantity = bean.getProductQuantity();
                productNumMap.put(key, productQuantity);
            });
        });

        MatchingRulesDto matchingRulesDto = new MatchingRulesDto();
        matchingRulesDto.setSourceType(sourceType);
        matchingRulesDto.setMemberLevelId(memberLevelId);
        matchingRulesDto.setCategoryList(categoryList);
        matchingRulesDto.setGoodsIds(goodsIds);
        matchingRulesDto.setProductCategoryAmountMap(productCategoryAmountMap);
        matchingRulesDto.setProductCategoryNumMap(productCategoryNumMap);
        matchingRulesDto.setProductAmountMap(productAmountMap);
        matchingRulesDto.setProductNumMap(productNumMap);
        return matchingRulesDto;
    }

    /**
     * 根据待匹配规则匹配赠品营销活动
     *
     * @param matchingRules 待匹配规则
     * @return 匹配到的赠品营销活动
     */
    private List<SmsBasicGiftsDto> getSmsBasicGiftsDto(MatchingRulesDto matchingRules, List<SmsBasicGiftsDto> smsBasicGiftsDtoList) {

        List<SmsBasicGiftsDto> collect = smsBasicGiftsDtoList.stream().filter(bean -> {
            // 1 首购礼 2 满购礼 3 单品礼赠
            Integer bigType = bean.getBigType();
            // 活动对象 1全部用户 2会员级别
            Integer activityUser = bean.getActiviUser();
            if (activityUser == 2) {
                Long memberLevelId = matchingRules.getMemberLevelId();
                String userLevel = bean.getUserLevel();
                String[] splits = userLevel.split(",");
                boolean allMatch = Arrays.stream(splits).allMatch(split -> split.equals(memberLevelId.toString()));
                if (!allMatch) {
                    return false;
                }
            }

            // 可使用商品 0指定分类 1指定商品
            Integer useGood = bean.getActiviGoods();
            if (useGood == 0) {
                List<Integer> categoryList = matchingRules.getCategoryList();
                String managementValue = bean.getManagementValue();
                String[] splits = managementValue.split(",");
                boolean allMatch = Arrays.stream(splits).allMatch(split -> categoryList.stream().allMatch(category -> category.equals(split)));
                if (!allMatch) {
                    return false;
                }
            } else if (useGood == 1) {
                List<Long> goodsIds = matchingRules.getGoodsIds();
                String goodValue = bean.getGoodValue();
                String[] splits = goodValue.split(",");
                boolean allMatch = Arrays.stream(splits).allMatch(split -> goodsIds.stream().allMatch(goodsId -> goodsId.equals(split)));
                if (!allMatch) {
                    return false;
                }
            } else {
                return false;
            }

            // 优惠类型 1消费金额  2购买件数
            Integer smallType = bean.getSmallType();
            // 获取规则 [{"fullPrice":0,"reducePrice":0}]
            String rules = bean.getRules();
            // 解析规则
            JSONArray objects = JSONArray.parseArray(rules);
            Optional<Object> first = objects.stream().findFirst();
            JSONObject rulesJSON = JSONObject.parseObject(first.get().toString());
            if (!rulesJSON.containsKey("fullPrice") || !rulesJSON.containsKey("reducePrice")) {
                return false;
            }

            BigDecimal fullPrice = new BigDecimal(rulesJSON.get("fullPrice").toString());

            boolean match = false;
            if (smallType == 1 && useGood == 0) {
                Map<Integer, BigDecimal> productCategoryAmountMap = matchingRules.getProductCategoryAmountMap();
                String managementValue = bean.getManagementValue();
                String[] splits = managementValue.split(",");
                match = Arrays.stream(splits).allMatch(split -> {
                    if (!productCategoryAmountMap.containsKey(split)) {
                        return false;
                    }
                    BigDecimal bigDecimal = productCategoryAmountMap.get(split);
                    return bigDecimal.compareTo(fullPrice) != -1;
                });
            } else if (smallType == 2 && useGood == 0) {
                Map<Integer, Integer> productCategoryNumMap = matchingRules.getProductCategoryNumMap();
                String goodValue = bean.getGoodValue();
                String[] splits = goodValue.split(",");
                match = Arrays.stream(splits).allMatch(split -> {
                    if (!productCategoryNumMap.containsKey(split)) {
                        return false;
                    }
                    Integer num = productCategoryNumMap.get(split);
                    return fullPrice.intValue() >= num;
                });
            } else if (smallType == 1 && useGood == 1) {
                Map<Long, BigDecimal> productAmountMap = matchingRules.getProductAmountMap();
                String managementValue = bean.getManagementValue();
                String[] splits = managementValue.split(",");
                match = Arrays.stream(splits).allMatch(split -> {
                    if (!productAmountMap.containsKey(split)) {
                        return false;
                    }
                    BigDecimal bigDecimal = productAmountMap.get(split);
                    return bigDecimal.compareTo(fullPrice) != -1;
                });
            } else if (smallType == 2 && useGood == 1) {
                Map<Long, Integer> productNumMap = matchingRules.getProductNumMap();
                String goodValue = bean.getGoodValue();
                String[] splits = goodValue.split(",");
                match = Arrays.stream(splits).allMatch(split -> {
                    if (!productNumMap.containsKey(split)) {
                        return false;
                    }
                    Integer num = productNumMap.get(split);
                    return fullPrice.intValue() >= num;
                });
            }

            return match;

        }).collect(Collectors.toList());

        return collect;
    }

}