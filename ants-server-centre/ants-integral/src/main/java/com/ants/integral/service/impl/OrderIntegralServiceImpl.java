package com.ants.integral.service.impl;

import com.ants.dubbo.api.base.member.IMemberService;
import com.ants.dubbo.api.service.integral.IOrderIntegralService;
import com.ants.module.member.UmsMemberLevelDto;
import com.ants.module.order.OmsOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

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

    @Override
    public OmsOrderDto calculation(OmsOrderDto omsOrderDto) {
        Long memberId = omsOrderDto.getMemberId();
        UmsMemberLevelDto umsMemberLevelDto = memberService.searchUmsMemberLevelByMemberId(memberId.intValue());
        // TODO 计算订单积分
        return omsOrderDto;
    }
}