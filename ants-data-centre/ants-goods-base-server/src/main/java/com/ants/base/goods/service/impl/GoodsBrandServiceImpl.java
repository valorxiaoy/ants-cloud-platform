package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodBrand;
import com.ants.base.goods.mapper.GoodsBrandMapper;
import com.ants.dubbo.api.base.goods.IGoodsBrandService;
import com.ants.module.goods.base.dto.GoodsBrandDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品品牌信息服务
 *
 * @author Yueyang
 * @create 2020-11-17 0:07
 **/
@Slf4j
@DubboService
public class GoodsBrandServiceImpl implements IGoodsBrandService {

    @Autowired
    private GoodsBrandMapper goodBrandMapper;

    @Override
    public GoodsBrandDto searchGoodsBrandByGoodsId(Integer goodsId) {
        try {
            GoodBrand goodBrand = goodBrandMapper.selectById(goodsId);
            if (goodBrand == null) {
                String exceptionMsg = String.format("商品品牌基础信息异常, 未找到商品品牌, 参数goodsId: %s", goodsId);
                throw new BusinessException(exceptionMsg);
            }
            GoodsBrandDto goodsBrandDto = new GoodsBrandDto();
            BeanUtils.copyBeanProp(goodsBrandDto, goodBrand);
            return goodsBrandDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }
}