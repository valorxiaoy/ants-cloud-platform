package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.ProductManagementEntity;
import com.ants.base.goods.mapper.ProductManagementMapper;
import com.ants.dubbo.api.base.product.IProductManagementService;
import com.ants.module.goods.base.dto.ProductManagementDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@DubboService
public class ProductManagementServiceImpl implements IProductManagementService {

    @Resource
    private ProductManagementMapper productManagementMapper;

    @Override
    public List<ProductManagementDto> searchProductManagementByStoreId(Integer storeId, Integer type) {
        List<ProductManagementDto> brandDtoList = new ArrayList<>();
        List<ProductManagementDto> bankDtoList = new ArrayList<>();
        QueryWrapper<ProductManagementEntity> queryCategory = new QueryWrapper();
        queryCategory.eq("pid", 0);
        List<ProductManagementEntity> categoryList = productManagementMapper.selectList(queryCategory);
        //只要15类别
        if (storeId == null) {
            List<ProductManagementDto> categoryDtos = BeanUtils.converteToDtoArray(categoryList, ProductManagementDto.class);
            //不要赠品 和  服务项目
            if (type == 4) {
                categoryDtos.stream().limit(13);
            }
            return categoryDtos;
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
    }


    @Override
    public ProductManagementDto getProductManagementDtoById(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品类别基础信息异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductManagementEntity productManagementEntity = productManagementMapper.selectById(id);
            if (productManagementEntity == null) {
                String exceptionMsg = String.format("商品类别基础信息异常, 未找到商品分类, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            ProductManagementDto productManagementDto = new ProductManagementDto();
            BeanUtils.copyBeanProp(productManagementDto, productManagementEntity);
            return productManagementDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean insertProductManagementByStoreId(ProductManagementDto productManagementDto) {
        try {
            QueryWrapper<ProductManagementEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", productManagementDto.getName());
            List<ProductManagementEntity> list = productManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 商品分类中已存在该名称, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            ProductManagementEntity productManagementEntity = new ProductManagementEntity();
            BeanUtils.copyProperties(productManagementDto, productManagementEntity);
            if (productManagementMapper.insert(productManagementEntity) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别添加失败, 参数productManagementEntity: %s", productManagementEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProductManagementByStoreId(ProductManagementDto productManagementDto) {
        try {
            QueryWrapper<ProductManagementEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", productManagementDto.getName());
            queryWrapper.notIn("id", productManagementDto.getId());
            List<ProductManagementEntity> list = productManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 商品分类中已存在该名称, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            ProductManagementEntity productManagementEntity = new ProductManagementEntity();
            BeanUtils.copyProperties(productManagementDto, productManagementEntity);
            if (productManagementMapper.updateById(productManagementEntity) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别修改失败, 参数productManagementEntity: %s", productManagementEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProductManagementById(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("商品类别基础信息异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
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
            if (productManagementMapper.updateById(productManagementEntity) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别删除失败, 参数productManagementEntity: %s", productManagementEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }
}