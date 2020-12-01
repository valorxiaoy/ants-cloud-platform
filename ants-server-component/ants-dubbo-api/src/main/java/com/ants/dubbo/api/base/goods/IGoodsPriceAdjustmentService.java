package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsPriceAdjustmentDto;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-20
 */

public interface IGoodsPriceAdjustmentService {
    /**
     * 查询商品调整价格单
     *
     * @param goodPriceAdjustmentDto
     * @return
     */
    List<GoodsPriceAdjustmentDto> searchGoodPriceAdjustment(GoodsPriceAdjustmentDto goodPriceAdjustmentDto);

    /**
     * 根据id 获取对应的调整价格订单明细
     *
     * @param id
     * @return
     */
    GoodsPriceAdjustmentDto searchGoodPriceAdjustmentById(Integer id);

    /**
     * 新增调整价格单
     *
     * @param list
     * @param storeId
     * @return
     */
    boolean createGoodPriceAdjustment(List<GoodsDetailedInformationDto> list, Integer storeId);

    /**
     * 修改调整价格单
     * @param orderSn
     * @param list
     * @param storeId
     * @return
     */
    boolean updateGoodPriceAdjustment(List<GoodsDetailedInformationDto> list, String orderSn,Integer storeId);

    /**
     * 删除调整价格单
     *
     * @param goodPriceAdjustmentDto
     * @return
     */
    boolean deleteGoodPriceAdjustment(GoodsPriceAdjustmentDto goodPriceAdjustmentDto);
}
