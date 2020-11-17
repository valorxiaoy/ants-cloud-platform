package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.recommend.dto.SmsHomeRecommendProductDto;

import java.util.List;

/**
 * 人气推荐
 */
public interface IGoodsRecommendInfoService {

    /**
     * 查询所有人气推荐
     *
     * @param storeId
     * @return
     */
    List<SmsHomeRecommendProductDto> searchSmsHomeRecommendProduct(Integer storeId);

}
