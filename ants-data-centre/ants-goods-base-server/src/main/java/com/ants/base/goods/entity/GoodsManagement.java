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
 * 商品分类(类别品牌系列)表
 */
@Data
@TableName("good_management")
public class GoodsManagement implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     *
     **/
    @TableField("sid")
    private String sid;


    /**
     * 名字
     **/
    @TableField("name")
    private String name;


    /**
     * 父id
     **/
    @TableField("pid")
    private String pid;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    private Date createTime;


    /**
     * 路径 以，隔开
     **/
    @TableField("path")
    private String path;


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
