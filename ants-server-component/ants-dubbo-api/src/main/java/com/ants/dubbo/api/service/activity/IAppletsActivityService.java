package com.ants.dubbo.api.service.activity;

import com.ants.module.activity.AppletsActivityDto;

import java.util.List;

/**
 * 赠品营销活动服务
 *
 * @author Yueyang
 */
public interface IAppletsActivityService {

    /**
     * 根据门店ID，查询所有活动列表
     *
     * @param storeId 门店ID
     * @return 活动列表
     */
    List<AppletsActivityDto> searchAppletsActivityByStoreId(String storeId);

    /**
     * 根据活动ID，查询活动内容
     *
     * @param id 活动ID
     * @return 活动对象
     */
    AppletsActivityDto searchAppletsActivityById(Integer id);

    /**
     * 参与活动
     *
     * @param appletsActivityId 活动ID
     * @return 是否参与成功
     */
    boolean partakeInActivityById(Integer appletsActivityId);
}
