package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-19
 */

public interface IGoodsDetailedInformationService {
    /**
     * 根据不同查询条件 查询
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    List<GoodsDetailedInformationDto> searchGoodDetailedInformationsByCondition(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 添加商品
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    boolean createGoodDetailedInformation(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 修改商品
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    boolean updateGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 删除商品
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    boolean deleteGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 批量修改商品
     *
     * @param list
     * @return
     */
    boolean batchUpdateGoodDetailedInformations(List<GoodsDetailedInformationDto> list);
}
