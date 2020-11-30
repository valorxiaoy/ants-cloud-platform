package com.ants.base.goods.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author 小米
 * @date 2020-11-19
 * 商品单位表
 */
@Data
@TableName("good_company")
public class GoodsCompany implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 商品单位
     **/
    @TableField("good_company")
    private String goodCompany;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    private String createTime;


    /**
     * 商户id
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     * 是否删除 0否 1是
     **/
    @TableField("is_delete")
    private Integer isDelete;


}
