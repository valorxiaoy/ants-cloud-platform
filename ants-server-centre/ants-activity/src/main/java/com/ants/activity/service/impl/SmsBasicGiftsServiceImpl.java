package com.ants.activity.service.impl;

import com.ants.activity.entity.SmsBasicGifts;
import com.ants.activity.mapper.SmsBasicGiftsMapper;
import com.ants.dubbo.api.service.activity.ISmsBasicGiftsService;
import com.ants.module.order.SmsBasicGiftsDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 活动服务
 *
 * @author Yueyang
 * @create 2020-11-28 10:35
 **/
@DubboService
public class SmsBasicGiftsServiceImpl implements ISmsBasicGiftsService {

    @Autowired
    private SmsBasicGiftsMapper smsBasicGiftsMapper;

    @Override
    public List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreId(Integer storeId) {
        if (storeId == null || storeId == 0) {
            throw new BusinessException(String.format("门店ID为空, 查询ID为: %s", storeId));
        }
        QueryWrapper<SmsBasicGifts> smsBasicGiftsQueryWrapper = new QueryWrapper<>();
        smsBasicGiftsQueryWrapper.eq("store_id", storeId);
        List<SmsBasicGifts> smsBasicGifts = smsBasicGiftsMapper.selectList(smsBasicGiftsQueryWrapper);
        if (smsBasicGifts == null || smsBasicGifts.size() == 0) {
            throw new BusinessException(String.format("未找到ID未%s的门店信息", storeId));
        }

        List<SmsBasicGiftsDto> smsBasicGiftsDtoList = BeanUtils.converteToDtoArray(smsBasicGifts, SmsBasicGiftsDto.class);
        return smsBasicGiftsDtoList;
    }

    /**
     * 查询线上活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    @Override
    public List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreIdOnline(Integer storeId) {

        if (storeId == null || storeId == 0) {
            throw new BusinessException(String.format("门店ID为空, 查询ID为: %s", storeId));
        }
        QueryWrapper<SmsBasicGifts> smsBasicGiftsQueryWrapper = new QueryWrapper<>();
        smsBasicGiftsQueryWrapper.eq("store_id", storeId);
        // 状态  0已开始 1未开始 2已结束  3 已关闭
        smsBasicGiftsQueryWrapper.eq("status", "0");
        // 平台 0全通用 1 线上 2线下
        smsBasicGiftsQueryWrapper.and(wrapper -> wrapper.eq("platform", "0").or().eq("platform", "1"));
        // 是否上线  0  1上
        smsBasicGiftsQueryWrapper.eq("if_xian", "1");
        // 删除状态：0->未删除；1->已删除
        smsBasicGiftsQueryWrapper.eq("delete_status", "0");
        List<SmsBasicGifts> smsBasicGifts = smsBasicGiftsMapper.selectList(smsBasicGiftsQueryWrapper);
        if (smsBasicGifts == null || smsBasicGifts.size() == 0) {
            throw new BusinessException(String.format("未找到ID未%s的门店信息", storeId));
        }
        List<SmsBasicGiftsDto> smsBasicGiftsDtoList = BeanUtils.converteToDtoArray(smsBasicGifts, SmsBasicGiftsDto.class);
        return smsBasicGiftsDtoList;
    }

    /**
     * 查询线下活动
     *
     * @param storeId 门店ID
     * @return 赠品营销活动列表
     */
    @Override
    public List<SmsBasicGiftsDto> searchSmsBasicGiftsByStoreIdOffline(Integer storeId) {
        if (storeId == null || storeId == 0) {
            throw new BusinessException(String.format("门店ID为空, 查询ID为: %s", storeId));
        }
        QueryWrapper<SmsBasicGifts> smsBasicGiftsQueryWrapper = new QueryWrapper<>();
        smsBasicGiftsQueryWrapper.eq("store_id", storeId);
        // 状态  0已开始 1未开始 2已结束  3 已关闭
        smsBasicGiftsQueryWrapper.eq("status", "0");
        // 平台 0全通用 1 线上 2线下
        smsBasicGiftsQueryWrapper.and(wrapper -> wrapper.eq("platform", "0").or().eq("platform", "2"));
        // 是否上线  0  1上
        smsBasicGiftsQueryWrapper.eq("if_xian", "1");
        // 删除状态：0->未删除；1->已删除
        smsBasicGiftsQueryWrapper.eq("delete_status", "0");
        List<SmsBasicGifts> smsBasicGifts = smsBasicGiftsMapper.selectList(smsBasicGiftsQueryWrapper);
        if (smsBasicGifts == null || smsBasicGifts.size() == 0) {
            throw new BusinessException(String.format("未找到ID未%s的门店信息", storeId));
        }
        List<SmsBasicGiftsDto> smsBasicGiftsDtoList = BeanUtils.converteToDtoArray(smsBasicGifts, SmsBasicGiftsDto.class);
        return smsBasicGiftsDtoList;
    }
}