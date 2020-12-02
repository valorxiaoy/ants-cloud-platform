package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameGoodCompanyDto
 * @Author小米
 * @Date2020/11/20 11:01
 * @Version 1.0
 **/
@Data
public class GoodsCompanyDto implements Serializable {
    private Integer id;


    /**
     * 商品单位
     **/
    private String goodCompany;


    /**
     * 创建时间
     **/
    private String createTime;


    /**
     * 商户id
     **/
    private Integer storeId;


    /**
     * 是否删除 0否 1是
     **/
    private Integer isDelete;
}
