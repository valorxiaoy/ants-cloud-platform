package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsPriceAdjustmentDao;
import io.renren.modules.generator.entity.GoodsPriceAdjustmentEntity;
import io.renren.modules.generator.service.GoodsPriceAdjustmentService;


@Service("goodsPriceAdjustmentService")
public class GoodsPriceAdjustmentServiceImpl extends ServiceImpl<GoodsPriceAdjustmentDao, GoodsPriceAdjustmentEntity> implements GoodsPriceAdjustmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsPriceAdjustmentEntity> page = this.page(
                new Query<GoodsPriceAdjustmentEntity>().getPage(params),
                new QueryWrapper<GoodsPriceAdjustmentEntity>()
        );

        return new PageUtils(page);
    }

}