package com.ants.module.goods.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品品牌信息
 *
 * @author Yueyang
 * @create 2020-11-16 21:03
 **/
@Data
public class GoodsBrandDto implements Serializable {

    private Integer id;

    /**
     商品品牌
     **/
    private String goodBrand;

    /**
     比例
     **/
    private String proportion;


    /**
     金额  元/个
     **/
    private BigDecimal price;

    /**

     **/
    private Date createTime;

}