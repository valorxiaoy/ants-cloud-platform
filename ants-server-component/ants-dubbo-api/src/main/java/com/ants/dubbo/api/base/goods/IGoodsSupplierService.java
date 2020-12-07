package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodsSupplierDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-19
 */

public interface IGoodsSupplierService {
    /**
     * 查询商品供应商
     *
     * @param storeId
     * @return
     */
    List<GoodsSupplierDto> searchGoodsSupplierByStoreId(Integer storeId);

    /**
     * 根据id 获取对象
     *
     * @param id
     * @return
     */
    GoodsSupplierDto searchGoodsSupplierById(Integer id);

    /**
     * 增加商品供应商
     *
     * @param goodSupplierDto
     * @return
     */
    boolean createGoodsSupplierByStoreId(GoodsSupplierDto goodSupplierDto);

    /**
     * 修改商品供应商
     *
     * @param goodSupplierDto
     * @return
     */
    boolean updateGoodsSupplierByStoreId(GoodsSupplierDto goodSupplierDto);

    /**
     * 删除商品供应商
     *
     * @param id
     * @return
     */
    boolean deleteGoodsSupplierById(Integer id);
}
