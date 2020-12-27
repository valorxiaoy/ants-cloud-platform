package com.ants.module.goods.base.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 商品品类信息表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class ProductManagementDto implements Serializable {
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
    /**
     * 所属门店ID
     */
    private Long storeId;
    /**
     * 是否禁用
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新人
     */
    private Long updateId;

}
