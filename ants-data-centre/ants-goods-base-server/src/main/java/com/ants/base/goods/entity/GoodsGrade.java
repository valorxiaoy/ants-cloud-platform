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
 * 商品登记表
 */
@Data
@TableName("good_grade")
public class GoodsGrade implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 等级
     **/
    @TableField("good_grade")
    private String goodGrade;


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
     * 是否删除
     **/
    @TableField("is_delete")
    private Integer isDelete;


}
