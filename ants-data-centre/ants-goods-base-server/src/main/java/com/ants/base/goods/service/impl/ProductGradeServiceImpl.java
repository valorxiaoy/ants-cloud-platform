package com.ants.base.goods.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductGradeEntity;
import com.ants.base.goods.mapper.ProductGradeMapper;
import com.ants.dubbo.api.base.product.IProductGradeService;
import com.ants.module.goods.base.dto.ProductGradeDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.SnowFlakeUtils;
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

    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);

    @Resource
    private ProductGradeMapper productGradeMapper;

    @Override
    public ProductGradeDto getProductGradeDto(Integer id) {
        try {
            if (id == null) {
                log.error("查询失败，商品等级ID为空。");
                throw new BusinessException("查询失败，商品等级ID为空。");
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", id);
            ProductGradeEntity productGradeEntity = productGradeMapper.selectOne(queryWrapper);
            if (productGradeEntity == null) {
                log.error("商品等级异常, 未找到商品等级, 参数id: %s", id);
                throw new BusinessException("商品等级异常, 未找到商品等级, 参数id: %s", id);
            }
            ProductGradeDto productGradeDto = new ProductGradeDto();
            BeanUtils.copyProperties(productGradeEntity, productGradeDto);
            return productGradeDto;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean updateProductGradeByStoreId(ProductGradeDto productGradeDto) {
        try {
            if (productGradeDto != null) {
                log.error("修改失败，商品等级对象为空。");
                throw new BusinessException("修改失败，商品等级对象为空。");
            }
            Long id = productGradeDto.getId();
            if (id == null) {
                log.error("修改失败，商品等级ID为空。");
                throw new BusinessException("修改失败，商品等级ID为空。");
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("grade_name", productGradeDto.getGradeName());
            queryWrapper.notIn("id", productGradeDto.getId());
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("修改失败，商品等级名称已存在。");
                throw new BusinessException("修改失败，商品等级名称已存在。");
            }
            //修改时间
            productGradeDto.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            BeanUtils.copyProperties(productGradeDto, productGradeEntity);
            int update = productGradeMapper.updateById(productGradeEntity);
            if (update == 1) {
                String msg = String.format("商品等级数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productGradeEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("修改失败，商品等级信息插入数据库失败。");
                throw new BusinessException("修改失败，商品等级信息修改数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("修改失败，商品等级信息修改多条数据。");
            } else {
                log.error("修改失败，商品等级信息插入发生未知异常。");
                throw new BusinessException("修改失败，商品等级信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public boolean insertProductGradeByStoreId(ProductGradeDto productGradeDto) {
        try {
            productGradeDto.setId(SNOW_FLAKE.nextId());
            if (productGradeDto != null) {
                log.error("创建失败，商品等级对象为空。");
                throw new BusinessException("创建失败，商品等级对象为空。");
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("grade_name", productGradeDto.getGradeName());
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                log.error("创建失败，商品等级名称已存在。");
                throw new BusinessException("创建失败，商品等级名称已存在。");
            }
            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            BeanUtils.copyProperties(productGradeDto, productGradeEntity);
            int insert = productGradeMapper.insert(productGradeEntity);
            if (insert == 1) {
                String msg = String.format("商品等级数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productGradeEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品等级信息插入数据库失败。");
                throw new BusinessException("创建失败，商品等级信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品等级信息插入多条数据。");
            } else {
                log.error("创建失败，商品等级信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品等级信息插入发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public List<ProductGradeDto> searchProductGradeByStoreId(Integer storeId) {
        try {
            if (storeId == null) {
                log.error("查询失败，商品等级storeId为空。");
                throw new BusinessException("查询失败，商品等级storeId为空。");
            }
            QueryWrapper<ProductGradeEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("store_id", storeId);
            List<ProductGradeEntity> list = productGradeMapper.selectList(queryWrapper);
            List<ProductGradeDto> productGradeDtoList = BeanUtils.converteToDtoArray(list, ProductGradeDto.class);
            return productGradeDtoList;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean deleteProductGradeDto(Long id) {
        try {
            if (id == null) {
                log.error("删除失败，商品等级ID为空。");
                throw new BusinessException("删除失败，商品等级ID为空。");
            }
            ProductGradeEntity productGradeEntity = new ProductGradeEntity();
            productGradeEntity.setId(id);
            productGradeEntity.setIsDelete(1);
            int delete = productGradeMapper.updateById(productGradeEntity);
            if (delete == 1) {
                String msg = String.format("商品等级数据删除成功， 删除信息:\n %s", JSONObject.toJSONString(productGradeEntity));
                log.info(msg);
            } else if (delete == 0) {
                log.error("删除失败，商品等级信息插入数据库失败。");
                throw new BusinessException("删除失败，商品等级信息修改数据库失败。");
            } else if (delete > 1) {
                throw new BusinessException("删除失败，商品等级信息修改多条数据。");
            } else {
                log.error("删除失败，商品等级信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品等级信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }
}