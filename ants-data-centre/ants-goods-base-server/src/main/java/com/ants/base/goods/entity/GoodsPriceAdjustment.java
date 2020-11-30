package com.ants.base.goods.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * @author 小米
 * @date 2020-11-20
 * 商品价格调整表
 */
@Data
@TableName("good_price_adjustment")
public class GoodsPriceAdjustment implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 调价单号
     **/
    @TableField("order_id")
    private String orderId;


    /**
     * 状态 0未审核 1审核
     **/
    @TableField("state")
    private Integer state;


    /**
     * 制单人id
     **/
    @TableField("user_id")
    private Integer userId;


    /**
     * 制单日期
     **/
    @TableField("maker_time")
    private Date makerTime;


    /**
     * 审核人id
     **/
    @TableField("auditor_user_id")
    private Integer auditorUserId;


    /**
     * 审核日期
     **/
    @TableField("auditor_time")
    private Date auditorTime;


    /**
     * 备注
     **/
    @TableField("note")
    private String note;


    /**
     *
     **/
    @TableField("create_time")
    private Date createTime;


    /**
     * 门店id
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     * 明细
     **/
    @TableField("detaile")
    private String detaile;


    /**
     * 生效门店
     **/
    @TableField("auditstore_id")
    private Integer auditstoreId;


    /**
     * 商品ID
     **/
    @TableField("good_id")
    private Integer goodId;


    /**
     * 制单人类型
     **/
    @TableField("user_type")
    private Integer userType;


    /**
     * 审核人类型
     **/
    @TableField("auditor_user_type")
    private Integer auditorUserType;


    /**
     * 删除状态：0->未删除；1->已删除
     **/
    @TableField("delete_status")
    private Integer deleteStatus;


}
