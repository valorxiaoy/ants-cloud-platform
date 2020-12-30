package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductPriceAdjustmentItemDto;

import java.util.List;

/**
 * 商品调价记录详情表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
public interface IProductPriceAdjustmentItemService {
    /**
     * 根据orderSn 获取对应的调价单
     *
     * @param orderSn
     * @return 调价单对象
     */
    List<ProductPriceAdjustmentItemDto> getProductPriceAdjustmentItemDto(Integer orderSn);

    /**
     * 描述： 修改调价单信息
     *
     * @param list:
     * @param orderSn:
     * @return boolean
     */
    boolean updateProductPriceAdjustmentItemDtoByStoreId(List<ProductPriceAdjustmentItemDto> list, Integer orderSn);

    /**
     * 描述： 创建调价单信息
     *
     * @param list:
     * @param orderSn:
     * @return boolean
     */
    boolean insertProductPriceAdjustmentItemDtoByStoreId(List<ProductPriceAdjustmentItemDto> list, Integer orderSn);

    /**
     * 根据id 删除对应的调价单
     *
     * @param id
     * @return boolean
     */
    boolean deleteProductPriceAdjustmentItemDto(Long id);
}

