package com.ants.base.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.ProductPriceAdjustmentEntity;
import com.ants.base.goods.mapper.ProductPriceAdjustmentMapper;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentItemService;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentService;
import com.ants.module.goods.base.dto.ProductPriceAdjustmentDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.SnowFlakeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Slf4j
@DubboService
public class ProductPriceAdjustmentServiceImpl implements IProductPriceAdjustmentService {
    // 基于分布式的订单获取方法
    private final static long dataCenterId = 1;  //数据中心
    private final static long machineId = 1;     //机器标识
    private static final SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);
    @Resource
    private ProductPriceAdjustmentMapper productPriceAdjustmentMapper;
    @Resource
    private IProductPriceAdjustmentItemService iProductPriceAdjustmentItemService;

    @Override
    public ProductPriceAdjustmentDto getProductPriceAdjustmentDto(Long id) {
        try {
            if (id == null) {
                log.error("查询失败，商品调价单ID为空。");
                throw new BusinessException("查询失败，商品调价单ID为空。");
            }
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = productPriceAdjustmentMapper.selectById(id);
            ProductPriceAdjustmentDto productPriceAdjustmentDto = new ProductPriceAdjustmentDto();
            BeanUtils.copyProperties(productPriceAdjustmentEntity, productPriceAdjustmentDto);
            return productPriceAdjustmentDto;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("修改失败，商品调价单信息对象为空。");
                throw new BusinessException("修改失败，商品调价单信息对象为空。");
            }
            String orderSn = productUnitDto.getBillSn();
            if (orderSn == null) {
                log.error("修改失败，商品调价单信息orderSn为空。");
                throw new BusinessException("修改失败，商品调价单信息orderSn为空。");
            }
            //存大表
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = new ProductPriceAdjustmentEntity();
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("bill_sn", productUnitDto.getBillSn());
            //修改调价单
            int update = productPriceAdjustmentMapper.update(productPriceAdjustmentEntity, queryWrapper);
            if (update == 1) {
                String msg = String.format("商品调价单数据修改成功， 修改信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentEntity));
                log.info(msg);
            } else if (update == 0) {
                log.error("修改失败，商品调价单信息插入数据库失败。");
                throw new BusinessException("修改失败，商品调价单信息修改数据库失败。");
            } else if (update > 1) {
                throw new BusinessException("修改失败，商品调价单信息修改多条数据。");
            } else {
                log.error("修改失败，商品调价单信息插入发生未知异常。");
                throw new BusinessException("修改失败，商品调价单信息修改发生未知异常。");
            }
            //删除明细
            if (!iProductPriceAdjustmentItemService.deleteProductPriceAdjustmentItemDto(productUnitDto.getId())) {
                log.error("修改失败, 商品调价单修改失败删除明细, 参数orderSn: %s", productUnitDto.getBillSn());
                String exceptionMsg = String.format("修改失败, 商品调价单修改失败删除明细, 参数orderSn: %s", productUnitDto.getBillSn());
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            if (!iProductPriceAdjustmentItemService.insertProductPriceAdjustmentItemDtoByStoreId(productUnitDto.getList(), productUnitDto.getId().intValue())) {
                log.error("修改失败, 商品调价单明细添加失败, 参数orderSn: %s", productUnitDto.getBillSn());
                String exceptionMsg = String.format("修改失败, 商品调价单明细添加失败, 参数orderSn: %s", productUnitDto.getBillSn());
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto) {
        try {
            if (productUnitDto != null) {
                log.error("添加失败，商品调价单信息对象为空。");
                throw new BusinessException("添加失败，商品调价单信息对象为空。");
            }
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //生成的入库单号
            String order_sn = "RK" + time.format(new Date());
            //存大表
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = new ProductPriceAdjustmentEntity();
            productUnitDto.setState(0);
            productUnitDto.setId(SNOW_FLAKE.nextId());
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            //制单日期
            productUnitDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //单号
            productUnitDto.setBillSn(order_sn);
            BeanUtils.copyProperties(productPriceAdjustmentEntity, productUnitDto);
            //添加调价单
            int insert = productPriceAdjustmentMapper.insert(productPriceAdjustmentEntity);
            if (insert == 1) {
                String msg = String.format("商品调价单数据创建成功， 创建信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentEntity));
                log.info(msg);
            } else if (insert == 0) {
                log.error("创建失败，商品调价单信息插入数据库失败。");
                throw new BusinessException("创建失败，商品调价单信息插入数据库失败。");
            } else if (insert > 1) {
                throw new BusinessException("创建失败，商品调价单信息插入多条数据。");
            } else {
                log.error("创建失败，商品调价单信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品调价单信息插入发生未知异常。");
            }
            //单号查对应的id
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("bill_sn", order_sn);
            ProductPriceAdjustmentEntity entity = productPriceAdjustmentMapper.selectOne(queryWrapper);
            if (entity == null) {
                log.error("商品调价单异常, 商品调价单无该单号, 参数billSn: %s", order_sn);
                String exceptionMsg = String.format("商品调价单异常, 商品调价单无该单号, 参数billSn: %s", order_sn);
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            if (!iProductPriceAdjustmentItemService.insertProductPriceAdjustmentItemDtoByStoreId(productUnitDto.getList(), entity.getId().intValue())) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数orderSn: %s", productUnitDto.getBillSn());
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }

    @Override
    public List<ProductPriceAdjustmentDto> searchProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productPriceAdjustmentDto) {
        try {
            if (productPriceAdjustmentDto.getStoreId() == null) {
                log.error("查询失败，商品调价单信息storeID为空。");
                throw new BusinessException("查询失败，商品调价单信息storeID为空。");
            }
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", productPriceAdjustmentDto.getStoreId());
            queryWrapper.eq("delete_status", 0);
            //审核状态
            if (productPriceAdjustmentDto.getState() != null) {
                queryWrapper.eq("state", productPriceAdjustmentDto.getState());
            }
            //时间
            if (productPriceAdjustmentDto.getStartTime() != null && productPriceAdjustmentDto.getEndTime() != null) {
                productPriceAdjustmentDto.setEndTime(productPriceAdjustmentDto.getEndTime().split(" ")[0] + " 23:59:59");
                queryWrapper.between("create_time", productPriceAdjustmentDto.getStartTime(), productPriceAdjustmentDto.getEndTime());
            }
            List<ProductPriceAdjustmentEntity> list = productPriceAdjustmentMapper.selectList(queryWrapper);
            list.forEach((bean) -> {
                //TODO 制单人 去账户表中拿 id

                //TODO 审核人 去账户表中拿 id
            });
            List<ProductPriceAdjustmentDto> productPriceAdjustmentDtos = BeanUtils.converteToDtoArray(list, ProductPriceAdjustmentDto.class);
            return productPriceAdjustmentDtos;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProductPriceAdjustmentDto(String orderSn) {
        try {
            if (orderSn == null) {
                log.error("删除失败，商品调价单信息orderSn为空。");
                throw new BusinessException("删除失败，商品调价单信息orderSn为空。");
            }
            //删价格改价表
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("bill_sn", orderSn);

            ProductPriceAdjustmentEntity entity = productPriceAdjustmentMapper.selectOne(queryWrapper);
            if (entity == null) {
                String exceptionMsg = String.format("商品调价单异常, 未找到的该单号信息, 参数orderSn: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }

            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = new ProductPriceAdjustmentEntity();
            productPriceAdjustmentEntity.setIsDelete(1);

            int delete = productPriceAdjustmentMapper.update(productPriceAdjustmentEntity, queryWrapper);
            if (delete == 1) {
                String msg = String.format("商品调价单数据删除成功， 创建信息:\n %s", JSONObject.toJSONString(productPriceAdjustmentEntity));
                log.info(msg);
            } else if (delete == 0) {
                log.error("删除失败，商品调价单信息插入数据库失败。");
                throw new BusinessException("删除失败，商品调价单信息修改数据库失败。");
            } else if (delete > 1) {
                throw new BusinessException("删除失败，商品调价单信息修改多条数据。");
            } else {
                log.error("删除失败，商品调价单信息插入发生未知异常。");
                throw new BusinessException("创建失败，商品调价单信息修改发生未知异常。");
            }

            if (!iProductPriceAdjustmentItemService.deleteProductPriceAdjustmentItemDto(entity.getId())) {
                log.error("商品调价单异常, 商品调价单明细删除失败, 参数order_id: %s", orderSn);
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细删除失败, 参数order_id: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (Exception exception) {
            String message = exception.getMessage();
            log.error(message);
            return false;
        }
    }
}