package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;

/**
 * 商品基础信息
 */
public interface IGoodsBaseInfoService {

    /**
     * 根据商品编号查询商品详情
     *
     * @param goodsId
     * @return
     */
    GoodsDetailedInformationDto searchGoodDetailedInfomationByGoodsId(String storeId, String goodsId);

}
