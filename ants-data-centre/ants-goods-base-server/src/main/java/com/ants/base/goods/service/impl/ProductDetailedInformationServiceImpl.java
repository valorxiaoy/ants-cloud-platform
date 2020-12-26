package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.ProductDetailedInformationDao;
import io.renren.modules.generator.entity.ProductDetailedInformationEntity;
import io.renren.modules.generator.service.ProductDetailedInformationService;


@Service("productDetailedInformationService")
public class ProductDetailedInformationServiceImpl extends ServiceImpl<ProductDetailedInformationDao, ProductDetailedInformationEntity> implements ProductDetailedInformationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductDetailedInformationEntity> page = this.page(
                new Query<ProductDetailedInformationEntity>().getPage(params),
                new QueryWrapper<ProductDetailedInformationEntity>()
        );

        return new PageUtils(page);
    }

}