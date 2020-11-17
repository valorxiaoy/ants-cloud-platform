package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.base.dto.GoodsBrandDto;

/**
 * 商品品牌信息
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IGoodsBrandService {

    /**
     * 根据商品ID查询品牌信息
     *
     * @param goodsId
     * @return
     */
    GoodsBrandDto searchGoodsBrandByGoodsId(Integer goodsId);
}
