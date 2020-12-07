package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsGradeDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-19
 */

public interface IGoodsGradeService {
    /**
     * 查询商品等级
     *
     * @param storeId
     * @return
     */
    List<GoodsGradeDto> searchGoodsGradeByStoreId(Integer storeId);

    /**
     * 根据id 获取该对象
     *
     * @param id
     * @return
     */
    GoodsGradeDto searchGoodsGradeById(Integer id);

    /**
     * 增加商品等级
     *
     * @param goodGradeDto
     * @return
     */
    boolean createGoodsGradeByStoreId(GoodsGradeDto goodGradeDto);

    /**
     * 修改商品等级
     *
     * @param goodGradeDto
     * @return
     */
    boolean updateGoodsGradeByStoreId(GoodsGradeDto goodGradeDto);

    /**
     * 删除商品等级
     *
     * @param id
     * @return
     */
    boolean deleteGoodsGradeById(Integer id);
}
