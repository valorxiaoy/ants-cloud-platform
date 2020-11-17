package com.ants.base.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuxiaozhi
 * @date 2020-05-25
商品根目录
 */
@Data
@TableName("good_brand")
public class GoodBrand implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     商品品牌
     **/
    @TableField( "good_brand")
    private String goodBrand;


    /**
     比例
     **/
    @TableField( "proportion")
    private String proportion;


    /**
     金额  元/个
     **/
    @TableField( "price")
    private BigDecimal price;


    /**

     **/
    @TableField( "create_time")
    private Date createTime;


}
