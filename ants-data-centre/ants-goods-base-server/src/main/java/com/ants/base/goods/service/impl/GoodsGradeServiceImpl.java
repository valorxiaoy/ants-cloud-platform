package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodsGradeDao;
import io.renren.modules.generator.entity.GoodsGradeEntity;
import io.renren.modules.generator.service.GoodsGradeService;


@Service("goodsGradeService")
public class GoodsGradeServiceImpl extends ServiceImpl<GoodsGradeDao, GoodsGradeEntity> implements GoodsGradeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsGradeEntity> page = this.page(
                new Query<GoodsGradeEntity>().getPage(params),
                new QueryWrapper<GoodsGradeEntity>()
        );

        return new PageUtils(page);
    }

}