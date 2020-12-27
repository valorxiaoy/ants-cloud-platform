package com.ants.module.goods.base.dto;

import com.ants.module.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品单位表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductUnitDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 单位编码
     */
    private String code;

}
