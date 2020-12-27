package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.ProductPriceAdjustmentItemEntity;
import com.ants.base.goods.mapper.ProductPriceAdjustmentItemMapper;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentItemService;
import com.ants.module.goods.base.dto.ProductPriceAdjustmentItemDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService
public class ProductPriceAdjustmentItemServiceImpl implements IProductPriceAdjustmentItemService {

    @Resource
    private ProductPriceAdjustmentItemMapper productPriceAdjustmentItemMapper;

    @Override
    public List<ProductPriceAdjustmentItemDto> getProductPriceAdjustmentItemDto(Integer orderSn) {
        try {
            if (orderSn == null) {
                String exceptionMsg = String.format("商品调价详情异常, 参数不正确, 参数orderSn: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductPriceAdjustmentItemEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_sn", orderSn);
            List<ProductPriceAdjustmentItemEntity> productPriceAdjustmentItemEntities = productPriceAdjustmentItemMapper.selectList(queryWrapper);
            List<ProductPriceAdjustmentItemDto> itemDtos = BeanUtils.converteToDtoArray(productPriceAdjustmentItemEntities, ProductPriceAdjustmentItemDto.class);
            return itemDtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateProductPriceAdjustmentItemDtoByStoreId(List<ProductPriceAdjustmentItemDto> list, Integer orderSn) {
        try {
            //删
            QueryWrapper<ProductPriceAdjustmentItemEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_sn", orderSn);
            if (productPriceAdjustmentItemMapper.delete(queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价详情异常, 参数不正确, 参数orderSn: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            //加
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品调价详情异常, 参数不正确, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            list.forEach(bean -> {
                bean.setOrderSn(orderSn.longValue());
                ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
                BeanUtils.copyProperties(bean, productPriceAdjustmentItemEntity);
                if (productPriceAdjustmentItemMapper.insert(productPriceAdjustmentItemEntity) < 0) {
                    String exceptionMsg = String.format("商品调价详情异常, 添加明细异常, 参数productPriceAdjustmentItemEntity: %s", productPriceAdjustmentItemEntity);
                    throw new BusinessException(exceptionMsg);
                }
            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertProductPriceAdjustmentItemDtoByStoreId(List<ProductPriceAdjustmentItemDto> list, Integer orderSn) {
        try {
            list.forEach(bean -> {
                bean.setOrderSn(orderSn.longValue());
                ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
                BeanUtils.copyProperties(bean, productPriceAdjustmentItemEntity);
                if (productPriceAdjustmentItemMapper.insert(productPriceAdjustmentItemEntity) < 0) {
                    String exceptionMsg = String.format("商品调价详情异常, 添加明细异常, 参数productPriceAdjustmentItemEntity: %s", productPriceAdjustmentItemEntity);
                    throw new BusinessException(exceptionMsg);
                }
            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteProductPriceAdjustmentItemDto(Integer id) {
        try {
            QueryWrapper<ProductPriceAdjustmentItemEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_sn", id);
            ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
            productPriceAdjustmentItemEntity.setIsDelete(1);
            if (productPriceAdjustmentItemMapper.update(productPriceAdjustmentItemEntity, queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价详情异常, 参数不正确, 参数orderSn: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }
}