package com.ants.module.goods.base.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassNameGoodGradeDto
 * @Author小米
 * @Date2020/11/20 10:54
 * @Version 1.0
 **/
@Data
public class GoodsGradeDto {
    private Integer id;


    /**
     * 等级
     **/
    private String goodGrade;


    /**
     * 创建时间
     **/
    private Date createTime;


    /**
     * 门店id
     **/
    private Integer storeId;


    /**
     * 是否删除
     **/
    private Integer isDelete;

}
