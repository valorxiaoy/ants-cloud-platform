package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsSkuValueDao;
import io.renren.modules.generator.entity.GoodsSkuValueEntity;
import io.renren.modules.generator.service.GoodsSkuValueService;


@Service("goodsSkuValueService")
public class GoodsSkuValueServiceImpl extends ServiceImpl<GoodsSkuValueDao, GoodsSkuValueEntity> implements GoodsSkuValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsSkuValueEntity> page = this.page(
                new Query<GoodsSkuValueEntity>().getPage(params),
                new QueryWrapper<GoodsSkuValueEntity>()
        );

        return new PageUtils(page);
    }

}