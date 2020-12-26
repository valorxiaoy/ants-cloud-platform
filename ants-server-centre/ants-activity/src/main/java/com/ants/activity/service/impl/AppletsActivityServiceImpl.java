package com.ants.activity.service.impl;

import com.ants.activity.entity.AppletsActivity;
import com.ants.activity.mapper.AppletsActivityMapper;
import com.ants.dubbo.api.service.activity.IAppletsActivityService;
import com.ants.module.activity.AppletsActivityDto;
import com.ants.tools.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 活动服务
 *
 * @author Yueyang
 * @create 2020-11-30 14:10
 **/
@Slf4j
@DubboService
public class AppletsActivityServiceImpl implements IAppletsActivityService {

    @Autowired
    private AppletsActivityMapper appletsActivityMapper;

    /**
     * 根据门店ID，查询所有活动列表
     *
     * @param storeId 门店ID
     * @return 活动列表
     */
    @Override
    public List<AppletsActivityDto> searchAppletsActivityByStoreId(String storeId) {
        return null;
    }

    /**
     * 根据活动ID，查询活动内容
     *
     * @param id 活动ID
     * @return 活动对象
     */
    @Override
    public AppletsActivityDto searchAppletsActivityById(Integer id) {
        AppletsActivity appletsActivity = appletsActivityMapper.selectById(id);
        AppletsActivityDto appletsActivityDto = new AppletsActivityDto();
        BeanUtils.copyBeanProp(appletsActivityDto, appletsActivity);
        return appletsActivityDto;
    }

    @Override
    public boolean partakeInActivityById(Integer appletsActivityId) {
        AppletsActivity appletsActivity = appletsActivityMapper.selectById(appletsActivityId);
        Integer activityPeople = appletsActivity.getActivityPeople() != null ? appletsActivity.getActivityPeople() + 1 : 1;
        appletsActivity.setActivityPeople(activityPeople);
        boolean isUpdate = appletsActivityMapper.updateById(appletsActivity) > 0;
        return isUpdate;
    }
}