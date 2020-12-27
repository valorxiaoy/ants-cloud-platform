package com.ants.module.goods.base.dto;


import com.ants.module.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品品类信息表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductManagementDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 所属父类
     */
    private Long pid;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 分类名称
     */
    private String name;

}
