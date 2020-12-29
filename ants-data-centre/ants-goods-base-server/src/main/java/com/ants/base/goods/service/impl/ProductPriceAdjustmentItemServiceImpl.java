package com.ants.base.goods.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductPriceAdjustmentItemEntity;
import com.ants.base.goods.mapper.ProductPriceAdjustmentItemMapper;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentItemService;
import com.ants.module.goods.base.dto.ProductPriceAdjustmentItemDto;
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
public class ProductPriceAdjustmentItemServiceImpl implements IProductPriceAdjustmentItemService {
    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);
    @Resource
    private ProductPriceAdjustmentItemMapper productPriceAdjustmentItemMapper;

    @Override
    public List<ProductPriceAdjustmentItemDto> getProductPriceAdjustmentItemDto(Integer orderSn) {
        try {
            if (orderSn == null) {
                log.error("查询失败，商品调价单orderSn为空。");
                throw new BusinessException("查询失败，商品调价单orderSn为空。");
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
            if (orderSn == null) {
                log.error("修改失败，商品调价单明细orderSn为空。");
                throw new BusinessException("修改失败，商品调价单明细orderSn为空。");
            }
            if (list.size() <= 0) {
                log.error("修改失败，商品调价单明细数据为空。");
                throw new BusinessException("修改失败，商品调价单明细数据为空。");
            }
            //删
            QueryWrapper<ProductPriceAdjustmentItemEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_sn", orderSn);

            if (productPriceAdjustmentItemMapper.delete(queryWrapper) < 0) {
                log.error("修改失败, 商品调价详情异常, 参数orderSn: %s", orderSn);
                throw new BusinessException("修改失败, 商品调价明细异常, 参数orderSn: %s", orderSn);
            }
            //加
            list.forEach(bean -> {
                bean.setOrderSn(orderSn.longValue());
                ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
                BeanUtils.copyProperties(bean, productPriceAdjustmentItemEntity);
                int insert = productPriceAdjustmentItemMapper.insert(productPriceAdjustmentItemEntity);
                if (insert == 1) {
                    String msg = String.format("商品调价单明细数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentItemEntity));
                    log.info(msg);
                } else if (insert == 0) {
                    log.error("修改失败，商品调价单明细信息插入数据库失败。");
                    throw new BusinessException("修改失败，商品调价单明细信息插入数据库失败。");
                } else if (insert > 1) {
                    throw new BusinessException("修改失败，商品调价单明细信息插入多条数据。");
                } else {
                    log.error("修改失败，商品调价单明细信息插入发生未知异常。");
                    throw new BusinessException("修改失败，商品调价单明细信息插入发生未知异常。");
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
            if (orderSn == null) {
                log.error("添加失败，商品调价单明细orderSn为空。");
                throw new BusinessException("添加失败，商品调价单明细orderSn为空。");
            }
            if (list.size() <= 0) {
                log.error("添加失败，商品调价单明细数据为空。");
                throw new BusinessException("添加失败，商品调价单明细数据为空。");
            }
            list.forEach(bean -> {
                bean.setId(SNOW_FLAKE.nextId());
                bean.setOrderSn(orderSn.longValue());
                ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
                BeanUtils.copyProperties(bean, productPriceAdjustmentItemEntity);
                int insert = productPriceAdjustmentItemMapper.insert(productPriceAdjustmentItemEntity);
                if (insert == 1) {
                    String msg = String.format("商品调价单明细数据添加成功， 创建信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentItemEntity));
                    log.info(msg);
                } else if (insert == 0) {
                    log.error("添加失败，商品调价单明细信息插入数据库失败。");
                    throw new BusinessException("添加失败，商品调价单明细信息插入数据库失败。");
                } else if (insert > 1) {
                    throw new BusinessException("添加失败，商品调价单明细信息插入多条数据。");
                } else {
                    log.error("添加失败，商品调价单明细信息插入发生未知异常。");
                    throw new BusinessException("添加失败，商品调价单明细信息插入发生未知异常。");
                }
            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteProductPriceAdjustmentItemDto(Long id) {
        try {
            if (id == null) {
                log.error("删除失败，商品调价单明细信息ID为空。");
                throw new BusinessException("删除失败，商品调价单明细信息ID为空。");
            }
            QueryWrapper<ProductPriceAdjustmentItemEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_sn", id);
            ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
            productPriceAdjustmentItemEntity.setIsDelete(1);
            int update = productPriceAdjustmentItemMapper.update(productPriceAdjustmentItemEntity, queryWrapper);
            if (update == 1) {
                String msg = String.format("商品调价单明细数据删除成功， 删除信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentItemEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("删除失败，商品调价单明细信息插入数据库失败。");
                throw new BusinessException("删除失败，商品调价单明细信息插入数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("删除失败，商品调价单明细信息插入多条数据。");
            } else {
                log.error("删除失败，商品调价单明细信息插入发生未知异常。");
                throw new BusinessException("删除失败，商品调价单明细信息插入发生未知异常。");
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }
}