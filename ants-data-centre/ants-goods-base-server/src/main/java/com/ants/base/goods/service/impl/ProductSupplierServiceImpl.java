package com.ants.base.goods.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductSupplierEntity;
import com.ants.base.goods.mapper.ProductSupplierMapper;
import com.ants.dubbo.api.base.product.IProductSupplierService;
import com.ants.module.goods.base.dto.ProductSupplierDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.SnowFlakeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService
public class ProductSupplierServiceImpl implements IProductSupplierService {
    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);
    @Resource
    private ProductSupplierMapper productSupplierMapper;

    @Override
    public ProductSupplierDto getProductSupplierDto(Long id) {
        try {
            if (id == null) {
                log.error("查询失败，商品供应商信息ID为空。");
                throw new BusinessException("查询失败，商品供应商信息ID为空。");
            }
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ProductSupplierEntity productSupplierEntity = productSupplierMapper.selectOne(queryWrapper);
            if (productSupplierEntity == null) {
                log.error("查询失败，商品供应商信息对象为空。");
                throw new BusinessException("查询失败，商品供应商信息对象为空。");
            }
            ProductSupplierDto productSupplierDto = new ProductSupplierDto();
            BeanUtils.copyProperties(productSupplierEntity, productSupplierDto);
            return productSupplierDto;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean updateProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("修改失败，商品供应商对象为空。");
                throw new BusinessException("修改失败，商品供应商对象为空。");
            }
            Long id = productUnitDto.getId();
            if (id == null) {
                log.error("修改失败，商品供应商ID为空。");
                throw new BusinessException("修改失败，商品供应商ID为空。");
            }
            //校验
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("supplier_phone", productUnitDto.getSupplierPhone()).or().eq("supplier_name", productUnitDto.getSupplierName()));
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("创建失败，商品供应商名称或者手机号已存在。");
                throw new BusinessException("创建失败，商品供应商名称或者手机号已存在。");
            }
            //修改
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            BeanUtils.copyProperties(productUnitDto, productSupplierEntity);
            int update = productSupplierMapper.updateById(productSupplierEntity);
            if (update == 1) {
                String msg = String.format("商品供应商数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productSupplierEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("修改失败，商品供应商信息插入数据库失败。");
                throw new BusinessException("修改失败，商品供应商信息修改数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("修改失败，商品供应商信息修改多条数据。");
            } else {
                log.error("修改失败，商品供应商信息插入发生未知异常。");
                throw new BusinessException("修改失败，商品供应商信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public boolean insertProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("添加失败，商品供应商对象为空。");
                throw new BusinessException("添加失败，商品供应商对象为空。");
            }
            productUnitDto.setId(SNOW_FLAKE.nextId());
            //校验
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("supplier_phone", productUnitDto.getSupplierPhone()).or().eq("supplier_name", productUnitDto.getSupplierName()));
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("创建失败，商品供应商名称或者手机号已存在。");
                throw new BusinessException("创建失败，商品供应商名称或者手机号已存在。");
            }
            //新增
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            BeanUtils.copyProperties(productUnitDto, productSupplierEntity);
            int insert = productSupplierMapper.insert(productSupplierEntity);
            if (insert == 1) {
                String msg = String.format("商品供应商数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productSupplierEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品供应商信息插入数据库失败。");
                throw new BusinessException("创建失败，商品供应商信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品供应商信息插入多条数据。");
            } else {
                log.error("创建失败，商品供应商信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品供应商信息插入发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public List<ProductSupplierDto> searchProductSupplierDtoByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                log.error("查询失败，商品供应商storeID为空。");
                throw new BusinessException("查询失败，商品供应商storeID为空。");
            }
            QueryWrapper<ProductSupplierEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<ProductSupplierEntity> list = productSupplierMapper.selectList(queryWrapper);
            List<ProductSupplierDto> goodSupplierDtos = BeanUtils.converteToDtoArray(list, ProductSupplierDto.class);
            return goodSupplierDtos;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean deleteProductSupplierDto(Long id) {
        try {
            if (id == null) {
                log.error("删除失败，商品供应商ID为空。");
                throw new BusinessException("删除失败，商品供应商ID为空。");
            }
            ProductSupplierEntity productSupplierEntity = new ProductSupplierEntity();
            productSupplierEntity.setId(Long.valueOf(id));
            productSupplierEntity.setIsDelete(1);
            int delete = productSupplierMapper.updateById(productSupplierEntity);
            if (delete == 1) {
                String msg = String.format("商品供应商数据删除成功， 删除信息:\n %s", JSONObject.toJSONString(productSupplierEntity));
                log.info(msg);
            } else if (delete == 0) {
                log.error("删除失败，商品供应商信息插入数据库失败。");
                throw new BusinessException("删除失败，商品供应商信息修改数据库失败。");
            } else if (delete > 1) {
                throw new BusinessException("删除失败，商品供应商信息修改多条数据。");
            } else {
                log.error("删除失败，商品供应商信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品供应商信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }
}