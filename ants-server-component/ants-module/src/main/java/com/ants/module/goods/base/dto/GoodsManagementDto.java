package com.ants.module.goods.base.dto;

import com.ants.module.BaseDto;

public class GoodsManagementDto extends BaseDto {

    private Integer id;


    /**
     * 编号
     **/
    private String sid;


    /**
     * 名字
     **/
    private String name;


    /**
     * 父
     **/
    private Integer pid;


    /**
     *
     **/
    private String createTime;

    /**
     * 路径
     */
    private String path;

    /**
     * 门店id
     */
    private Integer storeId;

}
