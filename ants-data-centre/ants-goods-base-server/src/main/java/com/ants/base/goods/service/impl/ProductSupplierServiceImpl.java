package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.ProductSupplierEntity;
import com.ants.base.goods.mapper.ProductSupplierMapper;
import com.ants.dubbo.api.base.product.IProductSupplierService;
import com.ants.module.goods.base.dto.ProductSupplierDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService
public class ProductSupplierServiceImpl implements IProductSupplierService {

    @Resource
    private ProductSupplierMapper productSupplierMapper;

    @Override
    public ProductSupplierDto getProductSupplierDto(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品供应商异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ProductSupplierEntity productSupplierEntity = productSupplierMapper.selectOne(queryWrapper);
            if (productSupplierEntity == null) {
                String exceptionMsg = String.format("商品供应商异常, 未找到该供应商, 参数productSupplierEntity: %s", productSupplierEntity);
                throw new BusinessException(exceptionMsg);
            }
            ProductSupplierDto productSupplierDto = new ProductSupplierDto();
            BeanUtils.copyProperties(productSupplierEntity, productSupplierDto);
            return productSupplierDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto) {
        try {
            //校验
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("supplier_phone", productUnitDto.getSupplierPhone()).or().eq("supplier_name", productUnitDto.getSupplierName()));
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商名字或者手机号已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            //修改
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            BeanUtils.copyProperties(productUnitDto, productSupplierEntity);
            if (productSupplierMapper.updateById(productSupplierEntity) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商修改失败, 参数productSupplierEntity: %s", productSupplierEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto) {
        try {
            //校验
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("supplier_phone", productUnitDto.getSupplierPhone()).or().eq("supplier_name", productUnitDto.getSupplierName()));
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商名字或者手机号已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            //新增
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            BeanUtils.copyProperties(productUnitDto, productSupplierEntity);
            if (productSupplierMapper.insert(productSupplierEntity) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商添加失败, 参数productSupplierEntity: %s", productSupplierEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductSupplierDto> searchProductSupplierDtoByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                String exceptionMsg = String.format("商品供应商异常, 参数不正确, 参数storeId: %s", storeId);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            List<ProductSupplierDto> goodSupplierDtos = BeanUtils.converteToDtoArray(list, ProductSupplierDto.class);
            return goodSupplierDtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteProductSupplierDto(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品供应商异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            productSupplierEntity.setId(Long.valueOf(id));
            productSupplierEntity.setIsDelete(1);
            if (productSupplierMapper.updateById(productSupplierEntity) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商删除失败, 参数productSupplierEntity: %s", productSupplierEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}