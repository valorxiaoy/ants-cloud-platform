package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsManagementDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-20
 */

public interface IGoodsManagementService {
    /**
     * 所有商品分类 品牌 系列
     *
     * @param storeId
     * @return
     */
    List<GoodsManagementDto> searchGoodsManagementByStoreId(String storeId);

    /**
     * 根据id 获取对应的对象
     *
     * @param id
     * @return
     */
    GoodsManagementDto searchGoodsManagementById(Integer id);

    /**
     * 添加分类
     *
     * @param goodsManagementDto
     * @return
     */
    boolean createGoodsManagementByStoreId(GoodsManagementDto goodsManagementDto);

    /**
     * 修改分类
     *
     * @param goodsManagementDto
     * @return
     */
    boolean updateGoodsManagementByStoreId(GoodsManagementDto goodsManagementDto);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    boolean deleteGoodsManagementById(Integer id);

    /**
     * 只要类别 不传参
     * 只要品牌  2
     * 只要系列  3
     *
     * @param storeId
     * @param type    2  品牌  3  系列   4  不要服务项目  赠品
     * @return
     */
    List<GoodsManagementDto> searchGoodsManagementBasicData(String storeId, Integer type);
}
