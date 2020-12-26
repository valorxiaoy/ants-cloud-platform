package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsSkuKeysDao;
import io.renren.modules.generator.entity.GoodsSkuKeysEntity;
import io.renren.modules.generator.service.GoodsSkuKeysService;


@Service("goodsSkuKeysService")
public class GoodsSkuKeysServiceImpl extends ServiceImpl<GoodsSkuKeysDao, GoodsSkuKeysEntity> implements GoodsSkuKeysService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsSkuKeysEntity> page = this.page(
                new Query<GoodsSkuKeysEntity>().getPage(params),
                new QueryWrapper<GoodsSkuKeysEntity>()
        );

        return new PageUtils(page);
    }

}