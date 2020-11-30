package com.ants.dubbo.api.service.activity;

import com.ants.module.activity.SmsBasicGiftsDto;

import java.util.List;

/**
 * 赠品营销活动服务
 *
 * @author Yueyang
 */
public interface ISmsBasicGiftsService {

    /**
     * 根据门店ID，查询所有赠品营销活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreId(Integer storeId);

    /**
     * 查询线上活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreIdOnline(Integer storeId);

    /**
     * 查询线下活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreIdOffline(Integer storeId);
}
