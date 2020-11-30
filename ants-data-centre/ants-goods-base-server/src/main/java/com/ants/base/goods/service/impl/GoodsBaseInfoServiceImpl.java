package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsDetailedInformation;
import com.ants.base.goods.mapper.GoodsDetailedInformationMapper;
import com.ants.dubbo.api.base.goods.IGoodsBaseInfoService;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品基础信息服务
 *
 * @author Yueyang
 * @create 2020-11-30 17:32
 **/
@DubboService
public class GoodsBaseInfoServiceImpl implements IGoodsBaseInfoService {

    @Autowired
    private GoodsDetailedInformationMapper goodsDetailedInformationMapper;

    @Override
    public GoodsDetailedInformationDto searchGoodDetailedInfomationByGoodsId(String storeId, String goodsId) {
        QueryWrapper<GoodsDetailedInformation> goodsDetailedInformationQueryWrapper = new QueryWrapper<>();
        goodsDetailedInformationQueryWrapper.eq("id", goodsId);
        goodsDetailedInformationQueryWrapper.eq("store_id", storeId);
        GoodsDetailedInformation goodsDetailedInformation = goodsDetailedInformationMapper.selectOne(goodsDetailedInformationQueryWrapper);
        GoodsDetailedInformationDto goodsDetailedInformationDto = new GoodsDetailedInformationDto();
        BeanUtils.copyBeanProp(goodsDetailedInformationDto, goodsDetailedInformation);
        return goodsDetailedInformationDto;
    }

    public GoodsDetailedInformationDto batchSearchOfGoods(List<String> productSnList) {
        //goodsDetailedInformationMapper.s
        return null;
    }
}