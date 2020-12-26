package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsSpecsDao;
import io.renren.modules.generator.entity.GoodsSpecsEntity;
import io.renren.modules.generator.service.GoodsSpecsService;


@Service("goodsSpecsService")
public class GoodsSpecsServiceImpl extends ServiceImpl<GoodsSpecsDao, GoodsSpecsEntity> implements GoodsSpecsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsSpecsEntity> page = this.page(
                new Query<GoodsSpecsEntity>().getPage(params),
                new QueryWrapper<GoodsSpecsEntity>()
        );

        return new PageUtils(page);
    }

}