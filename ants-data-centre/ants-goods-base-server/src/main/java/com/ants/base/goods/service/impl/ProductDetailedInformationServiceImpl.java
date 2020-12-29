package com.ants.base.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductDetailedInformationEntity;
import com.ants.base.goods.mapper.ProductDetailedInformationMapper;
import com.ants.dubbo.api.base.product.IProductDetailedInformationService;
import com.ants.module.goods.base.dto.ProductDetailedInformationDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品基础信息服务
 *
 * @author Yueyang
 * @create 2020-12-27 15:42
 **/
@Slf4j
@DubboService
@Transactional
public class ProductDetailedInformationServiceImpl implements IProductDetailedInformationService {

    @Autowired
    private ProductDetailedInformationMapper productDetailedInformationMapper;

    @Override
    public Boolean addProductDetailedInformation(ProductDetailedInformationDto productDetailedInformationDto) {
        try {
            if (productDetailedInformationDto != null) {
                log.error("创建失败，商品基础信息对象为空。");
                throw new BusinessException("创建失败，商品基础信息对象为空。");
            }
            Long id = productDetailedInformationDto.getId();
            if (id == null) {
                log.error("创建失败，商品基础信息ID为空。");
                throw new BusinessException("创建失败，商品基础信息ID为空。");
            }
            ProductDetailedInformationEntity productDetailedInformationEntity = new ProductDetailedInformationEntity();
            BeanUtils.copyBeanProp(productDetailedInformationEntity, productDetailedInformationDto);
            int insert = productDetailedInformationMapper.insert(productDetailedInformationEntity);
            if (insert == 1) {
                String msg = String.format("商品基础数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productDetailedInformationEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品基础信息插入数据库失败。");
                throw new BusinessException("创建失败，商品基础信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品基础信息插入多条数据。");
            } else {
                log.error("创建失败，商品基础信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品基础信息插入发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public Boolean deleteProductDetailedInformation(ProductDetailedInformationDto productDetailedInformationDto) {
        if (productDetailedInformationDto != null) {
            log.error("创建失败，商品基础信息对象为空。");
            throw new BusinessException("创建失败，商品基础信息对象为空。");
        }
        ProductDetailedInformationEntity productDetailedInformationEntity = new ProductDetailedInformationEntity();
        BeanUtils.copyBeanProp(productDetailedInformationEntity, productDetailedInformationDto);
        String value = JSONObject.toJSONString(productDetailedInformationEntity);
        JSONObject jsonObject = JSONObject.parseObject(value);
        Map<String, Object> innerMap = jsonObject.getInnerMap();


        productDetailedInformationMapper.deleteByMap(innerMap);
        return null;
    }

    @Override
    public Boolean updateProductDetailedInformation(ProductDetailedInformationDto productDetailedInformationDto) {
        return null;
    }

    @Override
    public ProductDetailedInformationDto searchProductDetailedInformation(ProductDetailedInformationDto productDetailedInformationDto) {
        return null;
    }

    @Override
    public List<ProductDetailedInformationDto> searchProductDetailedInformationList(ProductDetailedInformationDto productDetailedInformationDto) {
        return null;
    }
}