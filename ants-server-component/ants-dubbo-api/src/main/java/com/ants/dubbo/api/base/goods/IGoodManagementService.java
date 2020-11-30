package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsManagementDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-20
 */

public interface IGoodManagementService {
    /**
     * 商品分类
     * 一级 pid=0
     * 二级 pid=id
     * 三级 pid=id
     *
     * @param goodsManagementDto
     * @return
     */
    List<GoodsManagementDto> searchGoodManagement(GoodsManagementDto goodsManagementDto);

    /**
     * 根据id 获取对应的对象
     *
     * @param id
     * @return
     */
    GoodsManagementDto searchGoodManagementById(Integer id);

    /**
     * 添加分类
     *
     * @param goodsManagementDto
     * @return
     */
    boolean createGoodManagement(GoodsManagementDto goodsManagementDto);

    /**
     * 修改分类
     *
     * @param goodsManagementDto
     * @return
     */
    boolean updateGoodManagement(GoodsManagementDto goodsManagementDto);

    /**
     * 删除分类
     *
     * @param goodsManagementDto
     * @return
     */
    boolean deleteGoodManagement(GoodsManagementDto goodsManagementDto);
}
