package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.module.goods.base.dto.GoodsManagementDto;

import java.util.List;

/**
 * 商品基础信息
 */
public interface IGoodsBaseInfoService {

    /**
     * 查询所有商品品类
     *
     * @return
     */
    List<GoodsManagementDto> searchGoodsManagement(Integer storeId);

    /**
     * 查询分类中的所有商品信息
     *
     * @param storeId
     * @param goodCategosId
     * @return
     */
    List<GoodsDetailedInformationDto> searchGoodDetailedInformation(Integer storeId, Integer goodCategosId);

    /**
     * 根据商品编号查询商品详情
     *
     * @param goodsId
     * @return
     */
    GoodsDetailedInformationDto searchGoodDetailedInfomationByGoodsId(String storeId, String goodsId);

}
