package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.ProductManagementEntity;
import com.ants.base.goods.mapper.ProductManagementMapper;
import com.ants.dubbo.api.base.product.ProductManagementService;
import com.ants.module.goods.base.dto.ProductManagementDto;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@DubboService
public class ProductManagementServiceImpl implements ProductManagementService {

    @Resource
    private ProductManagementMapper productManagementMapper;

    @Override
    public List<ProductManagementDto> listProductManagementByStoreId(Integer storeId, Integer type) {
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
    public ProductManagementDto getProductManagementById(Integer id) {
        return null;
    }

    @Override
    public boolean insertProductManagementByStoreId(ProductManagementDto productManagementDto) {
        return false;
    }

    @Override
    public boolean updateProductManagementByStoreId(ProductManagementDto productManagementDto) {
        return false;
    }

    @Override
    public boolean deleteProductManagementById(Integer id) {
        return false;
    }
}