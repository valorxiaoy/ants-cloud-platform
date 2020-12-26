package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsManagementDao;
import io.renren.modules.generator.entity.GoodsManagementEntity;
import io.renren.modules.generator.service.GoodsManagementService;


@Service("goodsManagementService")
public class GoodsManagementServiceImpl extends ServiceImpl<GoodsManagementDao, GoodsManagementEntity> implements GoodsManagementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsManagementEntity> page = this.page(
                new Query<GoodsManagementEntity>().getPage(params),
                new QueryWrapper<GoodsManagementEntity>()
        );

        return new PageUtils(page);
    }

}