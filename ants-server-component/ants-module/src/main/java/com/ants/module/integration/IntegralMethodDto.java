package com.ants.module.integration;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘智
 * @description: 积分方式类
 * @date 2020-07-30
 * 积分方式
 */
@Data
public class IntegralMethodDto implements Serializable {


    private Integer id;


    /**
     * 方式名称
     **/
    private String method;

    /**
     * 门店id
     */
    private Integer storeId;


}
