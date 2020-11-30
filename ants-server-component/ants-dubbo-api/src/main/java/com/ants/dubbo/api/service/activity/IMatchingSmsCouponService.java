package com.ants.dubbo.api.service.activity;

import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.SmsCouponHistoryDto;

import java.util.List;

/**
 * 优惠卷活动服务
 *
 * @author Yueyang
 */
public interface IMatchingSmsCouponService {

    /**
     * 根据订单数据匹配优惠卷列表
     *
     * @param omsOrderDto 订单数据
     * @return 匹配后的优惠卷列表
     */
    List<SmsCouponHistoryDto> searchMatchingCouponByOrder(OmsOrderDto omsOrderDto);
}
