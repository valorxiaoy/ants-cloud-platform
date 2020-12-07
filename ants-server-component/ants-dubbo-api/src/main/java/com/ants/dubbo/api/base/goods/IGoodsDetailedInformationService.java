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
    List<GoodsDetailedInformationDto> searchGoodsDetailedInformationsByCondition(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    GoodsDetailedInformationDto searchGoodsDetailedInformationsById(Integer id);

    /**
     * 添加商品
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    boolean createGoodsDetailedInformationByStoreId(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 修改商品
     * 作用1-线上商品（上架 ，下架）
     *
     * @param goodsDetailedInformationDto
     * @return
     */
    boolean updateGoodsDetailedInformationsByStoreId(GoodsDetailedInformationDto goodsDetailedInformationDto);

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    boolean deleteGoodsDetailedInformationsById(Integer id);

    /**
     * 批量修改商品
     *
     * @param list
     * @return
     */
    boolean batchUpdateGoodsDetailedInformationsByStoreId(List<GoodsDetailedInformationDto> list);

}
