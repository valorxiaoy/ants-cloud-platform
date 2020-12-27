package com.ants.module.goods.base.dto;

import com.ants.module.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
@Data
public class AdminSysDictDataDto extends BaseDto implements Serializable {
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
     * 所属父类
     */
    private Long pid;
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private Long id;
}
