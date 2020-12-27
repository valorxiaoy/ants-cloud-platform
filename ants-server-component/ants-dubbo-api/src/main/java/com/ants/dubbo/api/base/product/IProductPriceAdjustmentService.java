package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductPriceAdjustmentDto;

import java.util.List;

/**
 * 商品调价记录表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
public interface IProductPriceAdjustmentService {
    /**
     * 根据id 获取对应的调价单
     *
     * @param id
     * @return 调价单对象
     */
    ProductPriceAdjustmentDto getProductPriceAdjustmentDto(Integer id);

    /**
     * 描述： 修改调价单信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean updateProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto);

    /**
     * 描述： 创建调价单信息
     *
     * @param productUnitDto:
     * @return boolean
     */
    boolean insertProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productUnitDto);

    /**
     * 根据多条件 获取对应的调价单
     *
     * @param productPriceAdjustmentDto
     * @return 多个调价单对象
     */
    List<ProductPriceAdjustmentDto> searchProductPriceAdjustmentDtoByStoreId(ProductPriceAdjustmentDto productPriceAdjustmentDto);

    /**
     * 根据orderSn 删除对应的调价单
     *
     * @param orderSn
     * @return boolean
     */
    boolean deleteProductPriceAdjustmentDto(String orderSn);
}

