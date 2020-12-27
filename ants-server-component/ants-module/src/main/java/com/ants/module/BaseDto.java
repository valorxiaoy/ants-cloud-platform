package com.ants.module;

import lombok.Data;

/**
 * 基础Dto模型
 *
 * @author Yueyang
 * @create 2020-11-12 18:38
 **/
@Data
public class BaseDto {

    private String sessionRedisKey;
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
    private String createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新人
     */
    private Long updateId;
}