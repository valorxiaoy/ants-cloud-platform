package com.ants.base.goods.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductManagementEntity;
import com.ants.base.goods.mapper.ProductManagementMapper;
import com.ants.dubbo.api.base.product.IProductManagementService;
import com.ants.module.goods.base.dto.ProductManagementDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.SnowFlakeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@DubboService
public class ProductManagementServiceImpl implements IProductManagementService {

    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);
    @Resource
    private ProductManagementMapper productManagementMapper;

    @Override
    public List<ProductManagementDto> searchProductManagementByStoreId(Integer storeId, Integer type) {
        try {
            if (type == null && (type != 1 || type != 2 || type != 3 || type != 4)) {
                log.error("查询失败，商品品系信息type值错误。");
                throw new BusinessException("查询失败，商品品系信息type值错误。");
            }
            if (storeId == null) {
                log.error("查询失败，商品品系信息storeId为空。");
                throw new BusinessException("查询失败，商品品系信息storeId为空。");
            }
            List<ProductManagementDto> brandDtoList = new ArrayList<>();
            List<ProductManagementDto> bankDtoList = new ArrayList<>();
            QueryWrapper<ProductManagementEntity> queryCategory = new QueryWrapper();
            queryCategory.eq("pid", 0);
            List<ProductManagementEntity> categoryList = productManagementMapper.selectList(queryCategory);
            if (categoryList == null) {
                log.error("查询失败，商品类别信息为空。");
                throw new BusinessException("查询失败，商品类别信息为空。");
            }
            //只要15类别
            List<ProductManagementDto> categoryDtos = BeanUtils.converteToDtoArray(categoryList, ProductManagementDto.class);
            if (type == 1) {
                return categoryDtos;
            }
            //不要赠品 和  服务项目
            if (type == 4) {
                return categoryDtos.stream().limit(13).collect(Collectors.toList());
            }
            if (type == 2) {
                //只要品牌
                categoryList.forEach((bean) -> {
                    QueryWrapper<ProductManagementEntity> queryBrand = new QueryWrapper();
                    queryBrand.eq("store_id", storeId);
                    queryBrand.eq("is_delete", 0);
                    queryBrand.eq("pid", bean.getId());
                    List<ProductManagementEntity> brandList = productManagementMapper.selectList(queryBrand);
                    List<ProductManagementDto> brandDtos = BeanUtils.converteToDtoArray(brandList, ProductManagementDto.class);
                    brandDtos.forEach((brand) -> {
                        ProductManagementDto dto = new ProductManagementDto();
                        BeanUtils.copyProperties(bean, dto);
                        brandDtoList.add(dto);
                    });
                });
                return brandDtoList;
            }
            if (type == 3) {
                //只要系列
                brandDtoList.forEach((bean) -> {
                    QueryWrapper<ProductManagementEntity> queryBrand = new QueryWrapper();
                    queryBrand.eq("store_id", storeId);
                    queryBrand.eq("is_delete", 0);
                    queryBrand.eq("pid", bean.getId());
                    List<ProductManagementEntity> brandList = productManagementMapper.selectList(queryBrand);
                    List<ProductManagementDto> brandDtos = BeanUtils.converteToDtoArray(brandList, ProductManagementDto.class);
                    brandDtos.forEach((brand) -> {
                        ProductManagementDto dto = new ProductManagementDto();
                        BeanUtils.copyProperties(bean, dto);
                        bankDtoList.add(dto);
                    });
                });
                return bankDtoList;
            }
            return null;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public ProductManagementDto getProductManagementDtoById(Long id) {
        try {
            if (id == null) {
                log.error("查询失败，商品品系信息ID为空。");
                throw new BusinessException("创建失败，商品品系信息ID为空。");
            }
            ProductManagementEntity productManagementEntity = productManagementMapper.selectById(id);
            if (productManagementEntity == null) {
                log.error("查询失败，商品品系信息对象为空。");
                throw new BusinessException("查询失败，商品品系信息对象为空。");
            }
            ProductManagementDto productManagementDto = new ProductManagementDto();
            BeanUtils.copyBeanProp(productManagementDto, productManagementEntity);
            return productManagementDto;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    public boolean insertProductManagementByStoreId(ProductManagementDto productManagementDto) {
        try {
            productManagementDto.setId(SNOW_FLAKE.nextId());
            if (productManagementDto != null) {
                log.error("创建失败，商品品系对象为空。");
                throw new BusinessException("创建失败，商品品系对象为空。");
            }
            QueryWrapper<ProductManagementEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", productManagementDto.getName());
            List<ProductManagementEntity> list = productManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                log.error("创建失败，商品品系名称已存在。");
                throw new BusinessException("创建失败，商品品系名称已存在。");
            }
            ProductManagementEntity productManagementEntity = new ProductManagementEntity();
            BeanUtils.copyProperties(productManagementDto, productManagementEntity);
            int insert = productManagementMapper.insert(productManagementEntity);
            if (insert == 1) {
                String msg = String.format("商品品系数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productManagementEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品品系信息插入数据库失败。");
                throw new BusinessException("创建失败，商品品系信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品品系信息插入多条数据。");
            } else {
                log.error("创建失败，商品品系信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品品系信息插入发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public boolean updateProductManagementByStoreId(ProductManagementDto productManagementDto) {
        try {
            if (productManagementDto != null) {
                log.error("修改失败，商品品系对象为空。");
                throw new BusinessException("修改失败，商品品系对象为空。");
            }
            Long id = productManagementDto.getId();
            if (id == null) {
                log.error("修改失败，商品品系ID为空。");
                throw new BusinessException("修改失败，商品品系ID为空。");
            }
            QueryWrapper<ProductManagementEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", productManagementDto.getName());
            queryWrapper.notIn("id", productManagementDto.getId());
            List<ProductManagementEntity> list = productManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                log.error("创建失败，商品品系名称已存在。");
                throw new BusinessException("创建失败，商品品系名称已存在。");
            }
            ProductManagementEntity productManagementEntity = new ProductManagementEntity();
            BeanUtils.copyProperties(productManagementDto, productManagementEntity);
            int update = productManagementMapper.updateById(productManagementEntity);
            if (update == 1) {
                String msg = String.format("商品品系数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productManagementEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("修改失败，商品品系信息插入数据库失败。");
                throw new BusinessException("修改失败，商品品系信息修改数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("修改失败，商品品系信息修改多条数据。");
            } else {
                log.error("修改失败，商品品系信息插入发生未知异常。");
                throw new BusinessException("修改失败，商品品系信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public boolean deleteProductManagementById(Integer id) {
        try {
            if (id == null) {
                log.error("删除失败，商品品系ID为空。");
                throw new BusinessException("删除失败，商品品系ID为空。");
            }
            //前十五大类不允许删除
            QueryWrapper<ProductManagementEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("pid", 0);
            List<ProductManagementEntity> list = productManagementMapper.selectList(queryWrapper);
            Stream<ProductManagementEntity> limit = list.stream().limit(15);
            boolean present = limit.filter(m -> m.getId() == id.longValue()).findAny().isPresent();
            if (present == true) {
                //TODO 前十五大类不允许删除 没返回
                return false;
            }
            ProductManagementEntity productManagementEntity = new ProductManagementEntity();
            productManagementEntity.setId(Long.valueOf(id));
            productManagementEntity.setIsDelete(1);
            int delete = productManagementMapper.updateById(productManagementEntity);
            if (delete == 1) {
                String msg = String.format("商品品系数据删除成功， 删除信息:\n %s", JSONObject.toJSONString(productManagementEntity));
                log.info(msg);
            } else if (delete == 0) {
                log.error("删除失败，商品品系信息插入数据库失败。");
                throw new BusinessException("删除失败，商品品系信息修改数据库失败。");
            } else if (delete > 1) {
                throw new BusinessException("删除失败，商品品系信息修改多条数据。");
            } else {
                log.error("删除失败，商品品系信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品品系信息修改发生未知异常。");
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }
}