package com.ants.module.goods.base.dto;


import com.ants.module.BaseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 供应商信息表
 *
 * @author 小米
 * @date 2020-12-27 16:09:04
 */
@Data
public class ProductSupplierDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
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

}
