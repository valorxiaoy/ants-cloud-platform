package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductManagementDto;

import java.util.List;

/**
 * 商品品类信息表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
public interface IProductManagementService {
    /**
     * 根据传type 获取storeId 下的对应的数据
     *
     * @param storeId 门店id
     * @param type    0->类别   2->品牌   3->系列    4->(类别)不要赠品 和  服务项目
     * @return
     */
    List<ProductManagementDto> searchProductManagementByStoreId(Integer storeId, Integer type);

    /**
     * 根据id  获取对象
     *
     * @param id
     * @return 对象
     */
    ProductManagementDto getProductManagementDtoById(Long id);

    /**
     * 新建类别 品牌  系列
     *
     * @param productManagementDto
     * @return boolean
     */
    boolean insertProductManagementByStoreId(ProductManagementDto productManagementDto);

    /**
     * 新建类别 品牌  系列
     *
     * @param productManagementDto
     * @return boolean
     */
    boolean updateProductManagementByStoreId(ProductManagementDto productManagementDto);

    /**
     * 根据id  删除对应的数据
     *
     * @param id
     * @return
     */
    boolean deleteProductManagementById(Integer id);
}

