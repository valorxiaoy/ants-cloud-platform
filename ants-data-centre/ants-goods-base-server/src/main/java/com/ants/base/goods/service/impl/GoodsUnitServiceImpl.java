package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsUnitDao;
import io.renren.modules.generator.entity.GoodsUnitEntity;
import io.renren.modules.generator.service.GoodsUnitService;


@Service("goodsUnitService")
public class GoodsUnitServiceImpl extends ServiceImpl<GoodsUnitDao, GoodsUnitEntity> implements GoodsUnitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsUnitEntity> page = this.page(
                new Query<GoodsUnitEntity>().getPage(params),
                new QueryWrapper<GoodsUnitEntity>()
        );

        return new PageUtils(page);
    }

}