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
    List<GoodsGradeDto> searchGoodGrade(Integer storeId);

    /**
     * 根据id 获取该对象
     *
     * @param id
     * @return
     */
    GoodsGradeDto searchGoodGradeById(Integer id);

    /**
     * 增加商品等级
     *
     * @param goodGradeDto
     * @return
     */
    boolean createGoodGrade(GoodsGradeDto goodGradeDto);

    /**
     * 修改商品等级
     *
     * @param goodGradeDto
     * @return
     */
    boolean updateGoodGrade(GoodsGradeDto goodGradeDto);

    /**
     * 删除商品等级
     *
     * @param goodGradeDto
     * @return
     */
    boolean deleteGoodGrade(GoodsGradeDto goodGradeDto);
}
