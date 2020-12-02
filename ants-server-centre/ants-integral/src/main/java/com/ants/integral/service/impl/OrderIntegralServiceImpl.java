package com.ants.integral.service.impl;

import com.ants.dubbo.api.base.goods.IGoodsBaseInfoService;
import com.ants.dubbo.api.base.member.IMemberService;
import com.ants.dubbo.api.base.store.IStoreService;
import com.ants.dubbo.api.service.integral.IOrderIntegralService;
import com.ants.integral.entity.IntegralRule;
import com.ants.integral.mapper.IntegralRuleMapper;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.module.integration.IntegralDetailsDto;
import com.ants.module.integration.IntegralRuleDto;
import com.ants.module.member.UmsMemberDto;
import com.ants.module.member.UmsMemberLevelDto;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.OmsOrderItemDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单积分计算服务
 *
 * @author Yueyang
 * @create 2020-11-17 0:24
 **/

@Slf4j
@DubboService
public class OrderIntegralServiceImpl implements IOrderIntegralService {

    @DubboReference
    private IMemberService memberService;

    @DubboReference
    private IGoodsBaseInfoService iGoodsBaseInfoService;

    @DubboReference
    private IStoreService iStoreService;
    @Resource
    private IntegralRuleMapper integralRuleMapper;

    @Override
    public OmsOrderDto calculation(OmsOrderDto omsOrderDto) {
        Long memberId = omsOrderDto.getMemberId();
        UmsMemberLevelDto umsMemberLevelDto = memberService.searchUmsMemberLevelByMemberId(memberId.intValue());
        //  计算订单积分
        // 通过会员等级id 查询对应的积分规则通过会员id
        IntegralRuleDto integralRuleDto = searchIntegraRuleByMemberLevelId(umsMemberLevelDto);
        OmsOrderDto newOmsOrderDto = doProductBrandPoints(integralRuleDto, omsOrderDto);
        return newOmsOrderDto;

    }

    /**
     * 描述：通过会员等级id 查询对应的积分规则
     *
     * @param umsMemberLevelDto:
     * @return com.ants.module.integration.IntegralRuleDto
     * @Author: 刘智
     */
    public IntegralRuleDto searchIntegraRuleByMemberLevelId(UmsMemberLevelDto umsMemberLevelDto) {
        try {
            QueryWrapper<IntegralRule> integralRuleQueryWrapper = new QueryWrapper<>();
            integralRuleQueryWrapper.eq("id", umsMemberLevelDto.getId());
            // TODO selectOne 支持一个会员等级，只能存在一条积分方式。
            // 通过传入的对象获取 会员等级id 通过会员等级id 查询积分方式
            List<IntegralRule> integralRules = integralRuleMapper.selectList(integralRuleQueryWrapper);
            if (integralRules.size() > 1) {
                String exceptionMsg = String.format("通过会员等级查找积分方式, 查到多个积分方式, 参数umsMemberLevelDto: %s", umsMemberLevelDto);
                throw new BusinessException(exceptionMsg);
            } else {
                IntegralRule integralRule = integralRules.stream().findFirst().get();
                IntegralRuleDto integralRuleDto = new IntegralRuleDto();
                BeanUtils.copyBeanProp(integralRuleDto, integralRule);
                return integralRuleDto;
            }
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }


    public OmsOrderDto doProductBrandPoints(IntegralRuleDto integralRuleDto, final OmsOrderDto omsOrderDto) {
        // 1. 判定消费特价商品积分
        // 2. 积分付款的金额不再积分
        // 3. 积分方式
        // 3.1 品牌
        // 3.2 金额
        // 4. 执行多倍
        // 筛选所有不能积分的商品
        List<OmsOrderItemDto> collect = omsOrderDto.getOrderItemList().stream().filter(bean -> {
            Integer productId = bean.getProductId();
            Integer storeId = omsOrderDto.getStoreId();
            GoodsDetailedInformationDto goodsDetailedInformationDto = iGoodsBaseInfoService.searchGoodDetailedInfomationByGoodsId(String.valueOf(storeId), String.valueOf(productId));
            return goodsDetailedInformationDto.getIfIntegral() != 0;
        }).collect(Collectors.toList());
        // 判定消费特价商品积分
        if (integralRuleDto.getDiscountEnable().equals(0)) {
            Integer orderType = omsOrderDto.getOrderType();
            if (!orderType.equals(0) && !orderType.equals(2)) {
                return omsOrderDto;
            }
        }
        // 积分付款的金额不再积分
        if (integralRuleDto.getPointpaymentEnable().equals(0)) {
            BigDecimal integrationAmount = omsOrderDto.getIntegrationAmount();
            //单个商品平均减掉的金额
            BigDecimal oneIntegrationAmount = integrationAmount.divide(new BigDecimal(omsOrderDto.getOrderItemList().size()), 20, BigDecimal.ROUND_HALF_UP);
            omsOrderDto.getOrderItemList().forEach(bean -> {
                // 获取积分后的价格
                BigDecimal subtract = bean.getProductPrice().subtract(oneIntegrationAmount);
                bean.setProductPrice(subtract);
            });
        }

        // 积分方式
        // 按消费金额
        if (integralRuleDto.getMethod() == 1) {
            if (omsOrderDto.getPayAmount().compareTo(integralRuleDto.getMoney()) > -1) {
                double integral = integralRuleDto.getIntegral() * ((omsOrderDto.getPayAmount().divide(integralRuleDto.getMoney())).intValue());
                omsOrderDto.setIntegration(integral);
            }
        }
        // 按品牌
        if (integralRuleDto.getMethod() == 4) {
            Map<String, List<OmsOrderItemDto>> productBrandCollect = collect.stream().collect(Collectors.groupingBy(bean -> bean.getProductBrand()));
            List<IntegralDetailsDto> brandIdsList = integralRuleDto.getBrandIdsList();
            productBrandCollect.forEach((key, value) -> {
                IntegralDetailsDto integralDetailsDto = brandIdsList.stream().filter(bean -> bean.getId().equals(key)).findAny().orElse(null);
                BigDecimal money = integralDetailsDto.getMoney();
                Double integral = integralDetailsDto.getIntegral();
                BigDecimal productPrice = value.stream().map(bean -> bean.getProductPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (productPrice.compareTo(money) > -1) {
                    // 算积分值
                    double integration = omsOrderDto.getIntegration();
                    double a = 0.0;
                    integration = integration + a;
                    omsOrderDto.setIntegration(integration);
                }
            });
        }
        //  算多倍
        UmsMemberDto umsMemberDto = memberService.searchUmsMember(omsOrderDto.getMemberId().intValue());
        Date birthday = umsMemberDto.getBirthday();
        Date date = new Date();
        Calendar ums = Calendar.getInstance();
        Calendar bir = Calendar.getInstance();
        //会员生日当天 birthday_enable 倍数 birthday_multiple
        if (birthday != null) {
            if (integralRuleDto.getBirthdayEnable() == 0) {
                if (ums.get(Calendar.MONTH) + 1 == bir.get(Calendar.MONTH) + 1 && ums.get(Calendar.DAY_OF_MONTH) == bir.get(Calendar.DAY_OF_MONTH)) {
                    omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getBirthdayMultiple());
                }
                return omsOrderDto;
            }
            //会员生日当月 birthdaymonth_enable 倍数 birthdaymonth_multiple
            if (integralRuleDto.getBirthdaymonthEnable() == 0) {
                if (ums.get(Calendar.MONTH) + 1 == bir.get(Calendar.MONTH) + 1) {
                    omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getBirthdaymonthMultiple());
                }
                return omsOrderDto;
            }
        }
        //每天 everyday_enable 倍数 everyday_multiple day_starttime day_endtime
        if (integralRuleDto.getEverydayEnable() == 0) {
            try {
                Date now = new SimpleDateFormat("HH:mm:ss").parse(new SimpleDateFormat("HH:mm:ss").format(date));
                Date start = new SimpleDateFormat("HH:mm:ss").parse(integralRuleDto.getDayStarttime());
                Date end = new SimpleDateFormat("HH:mm:ss").parse(integralRuleDto.getDayEndtime());
                if (now.getTime() >= start.getTime() && now.getTime() <= end.getTime()) {
                    omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getEverydayMultiple());
                }
                return omsOrderDto;
            } catch (BusinessException | ParseException e) {
                String exceptionMsg = String.format("多倍积分方式, 时间转换异常, 参数umsMemberLevelDto: %s", integralRuleDto.getDayStarttime());
                throw new BusinessException(exceptionMsg);
            }
        }
        //每周 weekly_enable 倍数 weekly_multiple 具体 weekday
        if (integralRuleDto.getWeeklyEnable() == 0) {
            //获取当前日期是周几 从周天开始算
            ums.setTime(date);
            int weekday = ums.get(Calendar.DAY_OF_WEEK);
            List<String> list = (List<String>) Arrays.asList(integralRuleDto.getWeekday().split(",")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList()).stream().filter(bean -> bean.equals(weekday - 1));
            if (list.size() > 0) {
                omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getWeeklyMultiple());
            }
            return omsOrderDto;
        }
        //每月 monthly_enable 倍数 monthly_multiple month
        if (integralRuleDto.getMonthlyEnable() == 0) {
            ums.setTime(date);
            List<String> list = (List<String>) Arrays.asList(integralRuleDto.getMonth().split(",")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList()).stream().filter(bean -> bean.equals(ums.get(Calendar.DAY_OF_MONTH)));
            if (list.size() > 0) {
                omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getMonthlyMultiple());
            }
            return omsOrderDto;
        }
        //特定节假日 appointedday_multiple 倍数 appointedday_multiple special_day_start special_day_end
        if (integralRuleDto.getAppointeddayEnable() == 0) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar start = Calendar.getInstance();
            start.setTime(integralRuleDto.getSpecialDayStart());
            integralRuleDto.getSpecialDayStart();
            Calendar end = Calendar.getInstance();
            end.setTime(integralRuleDto.getSpecialDayEnd());
            // 小于等于 <1  大于等于 >-1
            if (now.compareTo(start) > -1 && now.compareTo(end) < 1) {
                omsOrderDto.setIntegration(omsOrderDto.getIntegration() * integralRuleDto.getAppointeddayMultiple());
            }
            return omsOrderDto;
        }
        return omsOrderDto;
    }
}