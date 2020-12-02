package com.ants.dubbo.api.service.pay;

import com.ants.module.order.OmsOrderDto;

import java.util.Map;

/**
 * 富有支付-小程序支付
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IMiniProgramsPayServer {

    /**
     * 创建预支付订单
     *
     * @param openId      会员OpenId
     * @param omsOrderDto 订单数据
     * @return 富有支付平台-回应报文
     */
    Map<String, String> payOrder(String openId, OmsOrderDto omsOrderDto);
}
