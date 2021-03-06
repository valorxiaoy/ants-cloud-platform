package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 供应商信息表
 *
 * @author 小米
 * @date 2020-12-27 16:09:04
 */
@Data
@TableName("product_supplier")
public class ProductSupplierEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 联系人电话
     */
    private Integer supplierPhone;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人地址
     */
    private String address;
    /**
     * 供应商编码
     */
    private String supplierCode;
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
    private Date createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Long updateId;

}
