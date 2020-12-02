package com.ants.module.goods.base.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassNameGoodSupplierDto
 * @Author小米
 * @Date2020/11/20 11:04
 * @Version 1.0
 **/
@Data
public class GoodsSupplierDto {
    private Integer id;


    /**
     * 供应商名字
     **/
    private String supplier;


    /**
     * 手机号
     **/
    private String phoneCode;


    /**
     * 地址
     **/
    private String address;


    /**
     * 联系人名字
     **/
    private String contactName;


    /**
     * 创建时间
     **/
    private Date createTime;


    /**
     * 商户id
     **/
    private Integer storeId;


    /**
     * 是否删除 否 0 是1
     **/
    private Integer isDelete;
}
