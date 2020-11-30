package com.ants.dubbo.api.service.activity;

import com.ants.module.activity.SmsBasicGiftsDto;
import com.ants.module.order.OmsOrderDto;

import java.util.List;

/**
 * 赠品营销活动服务
 *
 * @author Yueyang
 */
public interface IMatchingSmsBasicGiftsService {

    /**
     * 根据订单匹配符合的赠品营销活动
     *
     * @param omsOrderDto 订单数据
     * @return 匹配到的赠品营销列表
     */
    List<SmsBasicGiftsDto> searchMatchingBasicGiftsByOrder(OmsOrderDto omsOrderDto);
}
