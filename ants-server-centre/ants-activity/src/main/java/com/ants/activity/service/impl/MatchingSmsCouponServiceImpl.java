package com.ants.activity.service.impl;

import com.ants.activity.entity.SmsCouponHistory;
import com.ants.activity.mapper.SmsCouponHistoryMapper;
import com.ants.dubbo.api.base.member.IMemberService;
import com.ants.dubbo.api.service.activity.IMatchingSmsCouponService;
import com.ants.dubbo.api.service.order.IOrderServer;
import com.ants.module.constant.ActivityConstant;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.OmsOrderItemDto;
import com.ants.module.order.SmsCouponHistoryDto;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

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
public class MatchingSmsCouponServiceImpl implements IMatchingSmsCouponService {

    @DubboReference
    private IMemberService memberService;

    @DubboReference
    private IOrderServer orderServer;

    @Autowired
    private SmsCouponHistoryMapper smsCouponHistoryMapper;

    /**
     * 根据订单数据匹配优惠卷列表
     *
     * @param omsOrderDto 订单数据
     * @return 匹配后的优惠卷列表
     */
    @Override
    public List<SmsCouponHistoryDto> searchMatchingCouponByOrder(OmsOrderDto omsOrderDto) {
        if (omsOrderDto == null || omsOrderDto.getOrderItemList() == null || omsOrderDto.getOrderItemList().size() == 0) {
            return null;
        }
        // 获取用户已拥有的优惠卷
        List<SmsCouponHistoryDto> smsCouponHistoryDtoList = searchCoupon(omsOrderDto);
        // 匹配可用优惠卷列表
        List<SmsCouponHistoryDto> newSmsCouponHistoryDtoList = checkCoupon(omsOrderDto, smsCouponHistoryDtoList);
        return newSmsCouponHistoryDtoList;
    }

    /**
     * 根据门店ID、会员ID查询全部可用的优惠卷列表
     *
     * @param omsOrderDto 订单数据
     * @return 全部可用的优惠卷列表
     */
    private List<SmsCouponHistoryDto> searchCoupon(OmsOrderDto omsOrderDto) {
        QueryWrapper<SmsCouponHistory> smsCouponQueryWrapper = new QueryWrapper<>();
        Long memberId = omsOrderDto.getMemberId();
        Integer storeId = omsOrderDto.getStoreId();
        smsCouponQueryWrapper.eq("store_id", storeId);
        smsCouponQueryWrapper.eq("member_id", memberId);
        // 使用状态：0->未使用；1->已使用；2->已过期
        smsCouponQueryWrapper.eq("use_status", 0);
        // 使用平台：0->全部；1->线上活动；2->线下活动
        smsCouponQueryWrapper.and(wrapper -> wrapper.eq("platform", 0).or().eq("platform", 1));
        List<SmsCouponHistory> smsCouponHistories = smsCouponHistoryMapper.selectList(smsCouponQueryWrapper);
        List<SmsCouponHistoryDto> smsCouponHistoryDtoList = BeanUtils.converteToDtoArray(smsCouponHistories, SmsCouponHistoryDto.class);
        return smsCouponHistoryDtoList;
    }

    private List<SmsCouponHistoryDto> checkCoupon(OmsOrderDto omsOrderDto, List<SmsCouponHistoryDto> smsCouponHistoryDtoList) {
        // 匹配适用的优惠卷
        List<SmsCouponHistoryDto> collect = smsCouponHistoryDtoList.stream().filter(bean -> {
            // 订单来源: 0->收银端 1->小程序
            Integer sourceType = omsOrderDto.getSourceType();
            // 使用平台：0->全部；1->线上活动；2->线下活动
            Integer platform = bean.getPlatform();
            // 收银端订单不能使用线上活动的优惠卷
            if (sourceType.equals(ActivityConstant.SOURCE_TYPE_CASHIER) && platform.equals(ActivityConstant.PLAT_FORM_ONLINE)) {
                return false;
            }
            // 小程序订单不能使用线下活动的优惠卷
            if (sourceType.equals(ActivityConstant.SOURCE_TYPE_MINI_PROGRAMS) && platform.equals(ActivityConstant.PLAT_FORM_OFFLINE)) {
                return false;
            }

            // 获取系统当前时间
            Date date = DateUtils.parseDate(DateUtils.getDate());
            // 优惠卷有效期 开始时间
            Date startTime = DateUtils.parseDate(bean.getStartTime());
            // 优惠卷有效期 结束时间
            Date endTime = DateUtils.parseDate(bean.getEndTime());
            // 判定有效期区间
            boolean effectiveDate = DateUtils.isEffectiveDate(date, startTime, endTime);
            if (!effectiveDate) {
                return false;
            }

            // 使用类型：0->全场通用；1->指定分类；2->指定商品
            Integer useType = bean.getUseType();
            boolean allMatch = true;
            if (useType == 0) {
                allMatch = checkCouponByPayAmount(bean, omsOrderDto);
            } else if (useType == 1) {
                allMatch = checkCouponByProductCategory(bean, omsOrderDto);
            } else if (useType == 2) {
                allMatch = checkCouponByGoods(bean, omsOrderDto);
            }

            return allMatch;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 校验全场通用的金额规则
     *
     * @param bean        优惠卷对象
     * @param omsOrderDto 订单数据
     * @return 是否满足条件
     */
    private boolean checkCouponByPayAmount(SmsCouponHistoryDto bean, OmsOrderDto omsOrderDto) {
        // 使用门槛, 0表示无门槛, 金额限制
        BigDecimal minPoint = bean.getMinPoint();
        // 应付金额
        BigDecimal payAmount = omsOrderDto.getPayAmount();
        if (minPoint.compareTo(BigDecimal.ZERO) != 0) {
            return minPoint.compareTo(payAmount) != 1;
        }
        return true;
    }

    /**
     * 校验分类下的金额规则
     *
     * @param bean        优惠卷对象
     * @param omsOrderDto 订单数据
     * @return 是否满足条件
     */
    private boolean checkCouponByProductCategory(SmsCouponHistoryDto bean, OmsOrderDto omsOrderDto) {
        // 子订单列表
        List<OmsOrderItemDto> orderItemList = omsOrderDto.getOrderItemList();
        // 使用门槛, 0表示无门槛, 金额限制
        BigDecimal minPoint = bean.getMinPoint();
        // 统计所有分类金额
        String categoryId = bean.getCategoryId();
        String[] categoryIds = categoryId.split(",");
        // 订单所含各分类中的商品信息
        Map<Integer, List<OmsOrderItemDto>> collect = orderItemList.stream().collect(Collectors.groupingBy(OmsOrderItemDto::getProductCategoryId));
        // 订单所含商品各分类消费金额
        Map<Integer, BigDecimal> productCategoryAmountMap = new HashMap<>();
        collect.forEach((key, value) -> {
            BigDecimal amount = value.stream().map(OmsOrderItemDto::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            productCategoryAmountMap.put(key, amount);
        });
        boolean allMatch = Arrays.stream(categoryIds).allMatch(split -> {
            int key = Integer.parseInt(split);
            if (productCategoryAmountMap.containsKey(key)) {
                BigDecimal bigDecimal = productCategoryAmountMap.get(key);
                return minPoint.compareTo(BigDecimal.ZERO) == 1 && (minPoint.compareTo(bigDecimal) == -1 || minPoint.compareTo(bigDecimal) == 0);
            }
            return false;
        });

        return allMatch;
    }

    /**
     * 校验商品下的金额规则
     *
     * @param bean        优惠卷对象
     * @param omsOrderDto 订单数据
     * @return 是否满足条件
     */
    private boolean checkCouponByGoods(SmsCouponHistoryDto bean, OmsOrderDto omsOrderDto) {
        // 子订单列表
        List<OmsOrderItemDto> orderItemList = omsOrderDto.getOrderItemList();
        // 使用门槛, 0表示无门槛, 金额限制
        BigDecimal minPoint = bean.getMinPoint();
        // 统计所有分类金额
        String goodId = bean.getGoodId();
        String[] goodIds = goodId.split(",");
        // 订单所含各分类中的商品信息
        Map<Integer, List<OmsOrderItemDto>> collect = orderItemList.stream().collect(Collectors.groupingBy(OmsOrderItemDto::getProductId));
        // 订单所含商品各分类消费金额
        Map<Integer, BigDecimal> productAmountMap = new HashMap<>();
        collect.forEach((key, value) -> {
            BigDecimal amount = value.stream().map(OmsOrderItemDto::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            productAmountMap.put(key, amount);
        });
        boolean allMatch = Arrays.stream(goodIds).allMatch(split -> {
            int key = Integer.parseInt(split);
            if (productAmountMap.containsKey(key)) {
                BigDecimal bigDecimal = productAmountMap.get(key);
                return minPoint.compareTo(BigDecimal.ZERO) != 1 && (minPoint.compareTo(bigDecimal) == -1 || minPoint.compareTo(bigDecimal) == 0);
            }
            return false;
        });

        return allMatch;
    }

}