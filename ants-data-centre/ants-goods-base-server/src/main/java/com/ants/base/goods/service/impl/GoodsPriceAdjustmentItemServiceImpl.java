package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsPriceAdjustmentItemDao;
import io.renren.modules.generator.entity.GoodsPriceAdjustmentItemEntity;
import io.renren.modules.generator.service.GoodsPriceAdjustmentItemService;


@Service("goodsPriceAdjustmentItemService")
public class GoodsPriceAdjustmentItemServiceImpl extends ServiceImpl<GoodsPriceAdjustmentItemDao, GoodsPriceAdjustmentItemEntity> implements GoodsPriceAdjustmentItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsPriceAdjustmentItemEntity> page = this.page(
                new Query<GoodsPriceAdjustmentItemEntity>().getPage(params),
                new QueryWrapper<GoodsPriceAdjustmentItemEntity>()
        );

        return new PageUtils(page);
    }

}