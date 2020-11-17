package com.ants.module.store;

import java.io.Serializable;
import java.util.List;

/**
 * 产品属性分类表
 *
 * @author Yueyang
 * @create 2020-11-16 8:05
 **/
public class PmsProductAttributeCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    List<PmsProductDto> goodsList;

    private Long id;

    private String name;

    private String pic;

    /**
     * 属性数量
     */
    private Integer attributeCount;

    /**
     * 参数数量
     */
    private Integer paramCount;

    /**
     * 列表样式 一列1 2 3个
     */
    private Integer style;

    private Integer showIndex;
}