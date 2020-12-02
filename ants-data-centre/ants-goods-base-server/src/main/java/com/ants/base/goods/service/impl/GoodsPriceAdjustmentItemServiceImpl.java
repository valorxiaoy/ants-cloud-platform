package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsPriceAdjustmentItem;
import com.ants.base.goods.mapper.GoodsPriceAdjustmentItemMapper;
import com.ants.dubbo.api.base.goods.IGoodsPriceAdjustmentItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author 小米
 * @date 2020-11-20
 */
@Slf4j
@DubboService
public class GoodsPriceAdjustmentItemServiceImpl extends ServiceImpl
        <GoodsPriceAdjustmentItemMapper, GoodsPriceAdjustmentItem> implements IGoodsPriceAdjustmentItemService {

    @Resource
    private GoodsPriceAdjustmentItemMapper goodPriceAdjustmentItemMapper;


}
