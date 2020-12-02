package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;

import java.util.List;

/**
 * 商品基础信息
 */
public interface IGoodsBaseInfoService {

    /**
     * 根据商品编号查询商品详情
     *
     * @param goodsId: 商品id
     * @param storeId: 门店id
     * @return <com.ants.module.goods.base.dto>
     */
    GoodsDetailedInformationDto searchGoodDetailedInfomationByGoodsId(String storeId, String goodsId);

    /**
     * 描述：根据商品条码list 批量查询
     *
     * @param productSnList: 商品条码集合
     * @param storeId:       门店id
     * @return java.util.List<com.ants.module.goods.base.dto.GoodsDetailedInformationDto>
     * @Date: 2020/11/30 18:54
     * @Author: 刘智
     */

    List<GoodsDetailedInformationDto> batchSearchOfGoods(List<String> productSnList, String storeId);

}
