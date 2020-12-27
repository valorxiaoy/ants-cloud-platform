package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.ProductUnitEntity;
import com.ants.base.goods.mapper.ProductUnitMapper;
import com.ants.dubbo.api.base.product.ProductUnitService;
import com.ants.module.goods.base.dto.ProductUnitDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@DubboService
public class ProductUnitServiceImpl implements ProductUnitService {
    @Resource
    private ProductUnitMapper productUnitMapper;

    @Override
    public ProductUnitDto getProductUnit(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品单位异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ProductUnitEntity productUnitEntity = productUnitMapper.selectOne(queryWrapper);
            if (productUnitEntity == null) {
                String exceptionMsg = String.format("商品单位异常, 未找到该单位, 参数productUnitEntity: %s", productUnitEntity);
                throw new BusinessException(exceptionMsg);
            }
            ProductUnitDto productUnitDto = new ProductUnitDto();
            BeanUtils.copyProperties(productUnitEntity, productUnitDto);
            return productUnitDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateProductUnitByStoreId(ProductUnitDto productUnitDto) {
        try {
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("unit_name", productUnitDto.getUnitName());
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品单位异常, 单位名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            BeanUtils.copyProperties(productUnitDto, productUnitEntity);
            if (productUnitMapper.updateById(productUnitEntity) < 0) {
                String exceptionMsg = String.format("商单位异常, 单位修改失败, 参数productUnitEntity: %s", productUnitEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertProductUnitByStoreId(ProductUnitDto productUnitDto) {
        try {
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("unit_name", productUnitDto.getUnitName());
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品单位异常, 单位名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            BeanUtils.copyProperties(productUnitDto, productUnitEntity);
            if (productUnitMapper.insert(productUnitEntity) < 0) {
                String exceptionMsg = String.format("商单位异常, 单位添加失败, 参数productUnitEntity: %s", productUnitEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductUnitDto> listProductUnitByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                String exceptionMsg = String.format("商品单位异常, 参数不正确, 参数storeId: %s", storeId);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            List<ProductUnitDto> dtos = BeanUtils.converteToDtoArray(list, ProductUnitDto.class);
            return dtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteProductUnit(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品单位异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            productUnitEntity.setId(Long.valueOf(id));
            productUnitEntity.setIsDelete(1);
            if (productUnitMapper.updateById(productUnitEntity) < 0) {
                String exceptionMsg = String.format("商品单位异常, 单位删除失败, 参数productUnitEntity: %s", productUnitEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}