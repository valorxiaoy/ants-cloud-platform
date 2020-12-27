package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.ProductGradeDto;

import java.util.List;

/**
 * 商品等级表
 *
 * @author 小米
 * @date 2020-12-26 16:34:51
 */
public interface ProductGradeService {
    /**
     * 根据id 获取对应的等级
     *
     * @param id
     * @return 等级对象
     */
    ProductGradeDto getProductGrade(Integer id);

    /**
     * 描述： 修改等级信息
     *
     * @param productGradeDto:
     * @return boolean
     */
    boolean updateProductGradeByStoreId(ProductGradeDto productGradeDto);

    /**
     * 描述： 创建等级信息
     *
     * @param productGradeDto:
     * @return boolean
     */
    boolean insertProductGradeByStoreId(ProductGradeDto productGradeDto);

    /**
     * 根据门店id 获取对应的等级
     *
     * @param storeId
     * @return 多个等级对象
     */
    List<ProductGradeDto> listProductGradeByStoreId(Integer storeId);

    /**
     * 根据id 删除对应的等级
     *
     * @param id
     * @return boolean
     */
    boolean deleteProductGrade(Integer id);
}

