package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.ProductGradeEntity;
import com.ants.base.goods.mapper.ProductGradeMapper;
import com.ants.dubbo.api.base.product.IProductGradeService;
import com.ants.module.goods.base.dto.ProductGradeDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@DubboService
public class ProductGradeServiceImpl implements IProductGradeService {

    @Resource
    private ProductGradeMapper productGradeMapper;

    @Override
    public ProductGradeDto getProductGradeDto(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品等级异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", id);
            ProductGradeEntity productGradeEntity = productGradeMapper.selectOne(queryWrapper);
            if (productGradeEntity == null) {
                String exceptionMsg = String.format("商品等级异常, 未找到商品等级, 参数productGradeEntity: %s", productGradeEntity);
                throw new BusinessException(exceptionMsg);
            }
            ProductGradeDto productGradeDto = new ProductGradeDto();
            BeanUtils.copyProperties(productGradeEntity, productGradeDto);
            return productGradeDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateProductGradeByStoreId(ProductGradeDto productGradeDto) {
        try {
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("grade_name", productGradeDto.getGradeName());
            queryWrapper.notIn("id", productGradeDto.getId());
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品等级异常, 等级名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            //修改时间
            productGradeDto.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            BeanUtils.copyProperties(productGradeDto, productGradeEntity);
            if (productGradeMapper.updateById(productGradeEntity) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级添加失败, 参数productGradeEntity: %s", productGradeEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertProductGradeByStoreId(ProductGradeDto productGradeDto) {
        try {
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("grade_name", productGradeDto.getGradeName());
            queryWrapper.notIn("id", productGradeDto.getId());
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品等级异常, 等级名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            BeanUtils.copyProperties(productGradeDto, productGradeEntity);
            if (productGradeMapper.insert(productGradeEntity) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级添加失败, 参数productGradeEntity: %s", productGradeEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductGradeDto> searchProductGradeByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                String exceptionMsg = String.format("商品等级异常, 参数不正确, 参数storeId: %s", storeId);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("store_id", storeId);
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            List<ProductGradeDto> productGradeDtoList = BeanUtils.converteToDtoArray(list, ProductGradeDto.class);
            return productGradeDtoList;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteProductGradeDto(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品等级异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            productGradeEntity.setId(Long.valueOf(id));
            productGradeEntity.setIsDelete(1);
            if (productGradeMapper.updateById(productGradeEntity) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级删除失败, 参数productGradeEntity: %s", productGradeEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}