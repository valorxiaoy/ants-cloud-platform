package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodSupplierDto;

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
    List<GoodSupplierDto> searchGoodSupplier(Integer storeId);

    /**
     * 根据id 获取对象
     *
     * @param id
     * @return
     */
    GoodSupplierDto searchGoodSupplierById(Integer id);

    /**
     * 增加商品供应商
     *
     * @param goodSupplierDto
     * @return
     */
    boolean createGoodSupplier(GoodSupplierDto goodSupplierDto);

    /**
     * 修改商品供应商
     *
     * @param goodSupplierDto
     * @return
     */
    boolean updateGoodSupplier(GoodSupplierDto goodSupplierDto);

    /**
     * 删除商品供应商
     *
     * @param goodSupplierDto
     * @return
     */
    boolean deleteGoodSupplier(GoodSupplierDto goodSupplierDto);
}
