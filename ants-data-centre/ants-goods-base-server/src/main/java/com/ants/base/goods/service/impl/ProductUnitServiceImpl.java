package com.ants.base.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductUnitEntity;
import com.ants.base.goods.mapper.ProductUnitMapper;
import com.ants.dubbo.api.base.product.IProductUnitService;
import com.ants.module.goods.base.dto.ProductUnitDto;
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
public class ProductUnitServiceImpl implements IProductUnitService {
    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);
    @Resource
    private ProductUnitMapper productUnitMapper;

    @Override
    public ProductUnitDto getProductUnitDtoById(Long id) {
        try {
            if (id == null) {
                log.error("查询失败，商品单位信息ID为空。");
                throw new BusinessException("查询失败，商品单位信息ID为空。");
            }
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            ProductUnitEntity productUnitEntity = productUnitMapper.selectOne(queryWrapper);
            if (productUnitEntity == null) {
                log.error("查询失败，商品单位信息对象为空。");
                throw new BusinessException("查询失败，商品单位信息对象为空。");
            }
            ProductUnitDto productUnitDto = new ProductUnitDto();
            BeanUtils.copyProperties(productUnitEntity, productUnitDto);
            return productUnitDto;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean updateProductUnitByStoreId(ProductUnitDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("修改失败，商品单位对象为空。");
                throw new BusinessException("修改失败，商品单位对象为空。");
            }
            Long id = productUnitDto.getId();
            if (id == null) {
                log.error("修改失败，商品单位ID为空。");
                throw new BusinessException("修改失败，商品单位ID为空。");
            }
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("unit_name", productUnitDto.getUnitName());
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("创建失败，商品单位名称已存在。");
                throw new BusinessException("创建失败，商品单位名称已存在。");
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            BeanUtils.copyProperties(productUnitDto, productUnitEntity);
            int update = productUnitMapper.updateById(productUnitEntity);
            if (update == 1) {
                String msg = String.format("商品单位数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productUnitEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("修改失败，商品单位信息插入数据库失败。");
                throw new BusinessException("修改失败，商品单位信息修改数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("修改失败，商品单位信息修改多条数据。");
            } else {
                log.error("修改失败，商品单位信息插入发生未知异常。");
                throw new BusinessException("修改失败，商品单位信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public boolean insertProductUnitByStoreId(ProductUnitDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("修改失败，商品单位对象为空。");
                throw new BusinessException("添加失败，商品单位对象为空。");
            }
            productUnitDto.setId(SNOW_FLAKE.nextId());
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", productUnitDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("unit_name", productUnitDto.getUnitName());
            queryWrapper.notIn("id", productUnitDto.getId());
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("创建失败，商品单位名称已存在。");
                throw new BusinessException("创建失败，商品单位名称已存在。");
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            BeanUtils.copyProperties(productUnitDto, productUnitEntity);
            int insert = productUnitMapper.insert(productUnitEntity);
            if (insert == 1) {
                String msg = String.format("商品单位数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productUnitEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品供单位信息插入数据库失败。");
                throw new BusinessException("创建失败，商品单位信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品单位信息插入多条数据。");
            } else {
                log.error("创建失败，商品单位信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品单位信息插入发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public List<ProductUnitDto> searchProductUnitDtoByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                log.error("查询失败，商品单位storeID为空。");
                throw new BusinessException("查询失败，商品单位storeID为空。");
            }
            QueryWrapper<ProductUnitEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<ProductUnitEntity> list = productUnitMapper.selectList(queryWrapper);
            List<ProductUnitDto> dtos = BeanUtils.converteToDtoArray(list, ProductUnitDto.class);
            return dtos;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean deleteProductUnit(Long id) {
        try {
            if (id == null) {
                log.error("删除失败，商品单位ID为空。");
                throw new BusinessException("删除失败，商品单位ID为空。");
            }
            ProductUnitEntity productUnitEntity = new ProductUnitEntity();
            productUnitEntity.setId(Long.valueOf(id));
            productUnitEntity.setIsDelete(1);
            int delete = productUnitMapper.updateById(productUnitEntity);
            if (delete == 1) {
                String msg = String.format("商品单位数据删除成功， 删除信息:\n %s", JSONObject.toJSONString(productUnitEntity));
                log.info(msg);
            } else if (delete == 0) {
                log.error("删除失败，商品单位信息插入数据库失败。");
                throw new BusinessException("删除失败，商品单位信息修改数据库失败。");
            } else if (delete > 1) {
                throw new BusinessException("删除失败，商品单位信息修改多条数据。");
            } else {
                log.error("删除失败，商品单位信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品单位信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }
}