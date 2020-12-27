package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductSupplierDto;

import java.util.List;

/**
 * 供应商信息表
 *
 * @author 小米
 * @date 2020-12-27 16:09:04
 */
public interface IProductSupplierService {
    /**
     * 根据id 获取对应的供应商
     *
     * @param id
     * @return 供应商对象
     */
    ProductSupplierDto getProductSupplierDto(Integer id);

    /**
     * 描述： 修改供应商信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean updateProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto);

    /**
     * 描述： 创建供应商信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean insertProductSupplierDtoByStoreId(ProductSupplierDto productUnitDto);

    /**
     * 根据门店id 获取对应的供应商
     *
     * @param storeId
     * @return 多个供应商对象
     */
    List<ProductSupplierDto> searchProductSupplierDtoByStoreId(Integer storeId);

    /**
     * 根据id 删除对应的供应商
     *
     * @param id
     * @return boolean
     */
    boolean deleteProductSupplierDto(Integer id);

}

