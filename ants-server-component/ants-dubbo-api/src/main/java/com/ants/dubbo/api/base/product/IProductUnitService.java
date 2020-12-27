package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductUnitDto;

import java.util.List;

/**
 * 商品单位表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
public interface IProductUnitService {
    /**
     * 根据id 获取对应的单位
     *
     * @param id
     * @return 单位对象
     */
    ProductUnitDto getProductUnitDtoById(Integer id);

    /**
     * 描述： 修改单位信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean updateProductUnitByStoreId(ProductUnitDto productUnitDto);

    /**
     * 描述： 创建单位信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean insertProductUnitByStoreId(ProductUnitDto productUnitDto);

    /**
     * 根据门店id 获取对应的单位
     *
     * @param storeId
     * @return 多个单位对象
     */
    List<ProductUnitDto> searchProductUnitDtoByStoreId(Integer storeId);

    /**
     * 根据id 删除对应的单位
     *
     * @param id
     * @return boolean
     */
    boolean deleteProductUnit(Integer id);
}

