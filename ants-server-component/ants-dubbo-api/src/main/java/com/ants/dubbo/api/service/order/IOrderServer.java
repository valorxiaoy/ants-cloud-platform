package com.ants.dubbo.api.service.order;

import com.ants.module.order.OmsOrderDto;

/**
 * 订单服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IOrderServer {

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderSn 订单编号
     * @return 订单对象
     */
    OmsOrderDto searchOrder(String orderSn);

    OmsOrderDto update(OmsOrderDto omsOrderDto);
}
