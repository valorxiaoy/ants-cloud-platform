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
 * @date 2020-11-19
 * 商品供应商表
 */
@Data
@TableName("good_supplier")
public class GoodsSupplier implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 供应商名字
     **/
    @TableField("supplier")
    private String supplier;


    /**
     * 手机号
     **/
    @TableField("phone_code")
    private String phoneCode;


    /**
     * 地址
     **/
    @TableField("address")
    private String address;


    /**
     * 联系人名字
     **/
    @TableField("contact_name")
    private String contactName;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    private Date createTime;


    /**
     * 商户id
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     * 是否删除 否 0 是1
     **/
    @TableField("is_delete")
    private Integer isDelete;


}
