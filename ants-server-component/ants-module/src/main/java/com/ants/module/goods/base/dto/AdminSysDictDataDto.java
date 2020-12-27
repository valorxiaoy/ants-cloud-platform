package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class AdminSysDictDataDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典编码
     */
    private Long dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典排序
     */
    private Integer dictSort;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 样式
     */
    private String cssClass;
    /**
     * 样式列表
     */
    private String listClass;
    /**
     * 是否默认
     */
    private String isDefault;
    /**
     * 0->禁用 1->启用
     */
    private String status;
    /**
     * 所属门店ID
     */
    private Long storeId;
    /**
     * 所属父类
     */
    private Long pid;
    /**
     * 是否禁用：0->未禁用；1->已禁用
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
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private Long id;
}
