package com.ants.dubbo.api.base.goods;


import com.ants.module.goods.base.dto.GoodCompanyDto;

import java.util.List;

/**
 * @author 小米
 * @date 2020-11-19
 */

public interface IGoodsCompanyService {
    /**
     * 查询商品单位
     *
     * @param storeId
     * @return
     */
    List<GoodCompanyDto> searchGoodCompany(Integer storeId);

    /**
     * 根据id获取对应的对象
     *
     * @param id
     * @return
     */
    GoodCompanyDto searchGoodCompanyById(Integer id);

    /**
     * 增加商品单位
     *
     * @param goodCompanyDto
     * @return
     */
    boolean createGoodCompany(GoodCompanyDto goodCompanyDto);

    /**
     * 修改商品单位
     *
     * @param goodCompanyDto
     * @return
     */
    boolean updateGoodCompany(GoodCompanyDto goodCompanyDto);

    /**
     * 删除商品单位
     *
     * @param goodCompanyDto
     * @return
     */
    boolean deleteGoodCompany(GoodCompanyDto goodCompanyDto);
}