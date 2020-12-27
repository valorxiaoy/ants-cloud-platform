package com.ants.module.goods.base.dto;


import com.ants.module.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品等级表
 *
 * @author 小米
 * @date 2020-12-26 16:34:51
 */
@Data
public class ProductGradeDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 等级名称
     */
    private String gradeName;
    /**
     * 等级编码
     */
    private String code;

}
