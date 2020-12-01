package com.ants.module.goods.base.dto;

import lombok.Data;

/**
 * @ClassNameGoodPriceAdjustmentDto
 * @Author小米
 * @Date2020/11/21 8:59
 * @Version 1.0
 **/
@Data
public class GoodsPriceAdjustmentDto {
    private Integer id;


    /**
     * 调价单号
     **/
    private String orderId;


    /**
     * 状态
     **/
    private Integer state;


    /**
     * 制单人id
     **/
    private Integer userId;


    /**
     * 制单日期
     **/
    private String makerTime;


    /**
     * 审核人id
     **/
    private Integer auditorUserId;


    /**
     * 审核日期
     **/
    private String auditorTime;


    /**
     * 备注
     **/
    private String note;


    /**
     *
     **/
    private String createTime;

    /**
     * 门店id
     */
    private Integer storeId;

    private String startTime;
    private String endTime;
    //制单人
    private String operation;
    // 审核人
    private String audit;

    private String detaile;
    //生效门店
    private Integer auditstoreId;
    //制单人类型  商户还员工
    private Integer userType;
    //审核人类型  商户还员工
    private Integer auditorUserType;
    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;
}
