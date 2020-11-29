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
    OmsOrderDto searchOrder(String storeId, String memberId, String orderSn);

    /**
     * 更新订单数据
     *
     * @param omsOrderDto 待更新订单数据
     * @return 更新后的订单对象
     */
    OmsOrderDto update(OmsOrderDto omsOrderDto);
}
