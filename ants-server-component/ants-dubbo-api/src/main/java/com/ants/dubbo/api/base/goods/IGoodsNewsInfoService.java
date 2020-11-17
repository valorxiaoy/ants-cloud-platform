package com.ants.dubbo.api.base.goods;

import com.ants.module.goods.news.dto.SmsHomeNewProductDto;

import java.util.List;

/**
 * 新品推荐
 */
public interface IGoodsNewsInfoService {

    /**
     * 查询所有新品推荐
     *
     * @return
     */
    List<SmsHomeNewProductDto> searchSmsHomeNewProduct(Integer storeId);

}
