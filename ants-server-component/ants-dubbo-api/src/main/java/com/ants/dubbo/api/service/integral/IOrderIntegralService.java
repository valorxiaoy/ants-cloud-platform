package com.ants.dubbo.api.service.integral;

import com.ants.module.order.OmsOrderDto;

/**
 * 订单积分计算服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IOrderIntegralService {

    /**
     * 计算订单获取积分
     *
     * @param omsOrderDto 订单数据
     * @return 计算后的订单数据
     */
    OmsOrderDto calculation(OmsOrderDto omsOrderDto);
}
