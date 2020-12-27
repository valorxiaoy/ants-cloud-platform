package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.ProductPriceAdjustmentEntity;
import com.ants.base.goods.mapper.ProductPriceAdjustmentMapper;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentItemService;
import com.ants.dubbo.api.base.product.IProductPriceAdjustmentService;
import com.ants.module.goods.base.dto.ProductPriceAdjustmentDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
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
    @Resource
    private ProductPriceAdjustmentMapper productPriceAdjustmentMapper;
    @Resource
    private IProductPriceAdjustmentItemService iProductPriceAdjustmentItemService;

    @Override
    public ProductPriceAdjustmentDto getProductPriceAdjustmentDto(Integer id) {
        try {
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = productPriceAdjustmentMapper.selectById(id);
            if (productPriceAdjustmentEntity == null) {
                String exceptionMsg = String.format("商品调价单异常, 未找到商品调价单, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductPriceAdjustmentDto productPriceAdjustmentDto = new ProductPriceAdjustmentDto();
            BeanUtils.copyProperties(productPriceAdjustmentEntity, productPriceAdjustmentDto);
            return productPriceAdjustmentDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto) {
        try {
            //存大表
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = new ProductPriceAdjustmentEntity();
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("bill_sn", productUnitDto.getBillSn());
            //修改调价单
            if (productPriceAdjustmentMapper.update(productPriceAdjustmentEntity, queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单修改失败, 参数productPriceAdjustmentEntity: %s", productPriceAdjustmentEntity);
                throw new BusinessException(exceptionMsg);
            }
            //删除明细
            if (!iProductPriceAdjustmentItemService.deleteProductPriceAdjustmentItemDto(productUnitDto.getId().intValue())) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单修改失败删除明细, 参数orderSn: %s", productUnitDto.getId());
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            if (!iProductPriceAdjustmentItemService.insertProductPriceAdjustmentItemDtoByStoreId(productUnitDto.getList(), productUnitDto.getId().intValue())) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数orderSn: %s", productUnitDto.getId());
                throw new BusinessException(exceptionMsg);
            }
//            ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
//            productUnitDto.getList().forEach((bean) -> {
//                //商品id
//                productPriceAdjustmentItemEntity.setGoodsCode(bean.getId());
//                //批发价
//                productPriceAdjustmentItemEntity.setWholesalePrice(bean.getWholesalePrice());
//                //零售价
//                productPriceAdjustmentItemEntity.setRetailPrice(bean.getRetailPrice());
//                //单号
//                productPriceAdjustmentItemEntity.setOrderSn(productUnitDto.getId());
//                //添加调价单明细
//                if (productPriceAdjustmentItemMapper. (productPriceAdjustmentItemEntity) < 0){
//                    String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数productPriceAdjustmentItemEntity: %s", productPriceAdjustmentItemEntity);
//                    throw new BusinessException(exceptionMsg);
//                }
//            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto) {
        try {
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //生成的入库单号
            String order_sn = "RK" + time.format(new Date());
            //存大表
            ProductPriceAdjustmentEntity productPriceAdjustmentEntity = new ProductPriceAdjustmentEntity();
            productUnitDto.setState(0);
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            //制单日期
            productUnitDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //单号
            productUnitDto.setBillSn(order_sn);
            BeanUtils.copyProperties(productPriceAdjustmentEntity, productUnitDto);
            //添加调价单
            if (productPriceAdjustmentMapper.insert(productPriceAdjustmentEntity) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单创建失败, 参数productPriceAdjustmentEntity: %s", productPriceAdjustmentEntity);
                throw new BusinessException(exceptionMsg);
            }
            //单号查对应的id
            QueryWrapper<ProductPriceAdjustmentEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("bill_sn", order_sn);
            ProductPriceAdjustmentEntity entity = productPriceAdjustmentMapper.selectOne(queryWrapper);
            if (entity == null) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单无该单号, 参数billSn: %s", order_sn);
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            if (!iProductPriceAdjustmentItemService.insertProductPriceAdjustmentItemDtoByStoreId(productUnitDto.getList(), entity.getId().intValue())) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数orderSn: %s", productUnitDto.getId());
                throw new BusinessException(exceptionMsg);
            }
//            //存明细表
//            ProductPriceAdjustmentItemEntity productPriceAdjustmentItemEntity = new ProductPriceAdjustmentItemEntity();
//            productUnitDto.getList().forEach((bean) -> {
//                //商品id
//                productPriceAdjustmentItemEntity.setGoodsCode(bean.getId());
//                //批发价
//                productPriceAdjustmentItemEntity.setWholesalePrice(bean.getWholesalePrice());
//                //零售价
//                productPriceAdjustmentItemEntity.setRetailPrice(bean.getRetailPrice());
//                //单号
//                productPriceAdjustmentItemEntity.setOrderSn(entity.getId());
//                //添加调价单明细
//                if (productPriceAdjustmentItemMapper.insert(productPriceAdjustmentItemEntity) < 0) {
//                    String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数productPriceAdjustmentItemEntity: %s", productPriceAdjustmentItemEntity);
//                    throw new BusinessException(exceptionMsg);
//                }
//            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductPriceAdjustmentDto> searchProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productPriceAdjustmentDto) {
        try {
            if (productPriceAdjustmentDto.getStoreId() == null) {
                String exceptionMsg = String.format("商品调价异常, 参数不正确, 参数storeId: %s", productPriceAdjustmentDto.getStoreId());
                throw new BusinessException(exceptionMsg);
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
                queryWrapper.between("create_time", productPriceAdjustmentDto.getStartTime(), productPriceAdjustmentDto.getEndTime());
            }
            List<ProductPriceAdjustmentEntity> list = productPriceAdjustmentMapper.selectList(queryWrapper);
            list.forEach((bean) -> {
                //TODO 制单人 去账户表中拿 id

                //TODO 审核人 去账户表中拿 id
            });
            List<ProductPriceAdjustmentDto> productPriceAdjustmentDtos = BeanUtils.converteToDtoArray(list, ProductPriceAdjustmentDto.class);
            return productPriceAdjustmentDtos;
        } catch (
                BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProductPriceAdjustmentDto(String orderSn) {
        try {
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
            if (productPriceAdjustmentMapper.update(productPriceAdjustmentEntity, queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单删除失败, billSn: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            if (!iProductPriceAdjustmentItemService.deleteProductPriceAdjustmentItemDto(entity.getId().intValue())) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细删除失败, 参数order_id: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}