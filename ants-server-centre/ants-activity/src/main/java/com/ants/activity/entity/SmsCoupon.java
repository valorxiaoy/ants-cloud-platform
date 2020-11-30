package com.ants.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 小米
 * @date 2020-08-06
 * 优惠券
 */
@Data
@TableName("sms_coupon")
public class SmsCoupon implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     **/
    @TableField("type")
    private Integer type;


    /**
     *
     **/
    @TableField("name")
    private String name;


    /**
     * 使用平台：0->全部；1->线上活动；2->线下活动
     **/
    @TableField("platform")
    private Integer platform;


    /**
     * 数量
     **/
    @TableField("count")
    private Integer count;


    /**
     * 面值
     **/
    @TableField("amount")
    private BigDecimal amount;


    /**
     * 每人限领张数
     **/
    @TableField("per_limit")
    private Integer perLimit;


    /**
     * 使用门槛；0表示无门槛
     **/
    @TableField("min_point")
    private BigDecimal minPoint;


    /**
     *
     **/
    @TableField("start_time")
    private String startTime;


    /**
     *
     **/
    @TableField("end_time")
    private String endTime;


    /**
     * 使用类型：0->全场通用；1->指定分类；2->指定商品
     **/
    @TableField("use_type")
    private Integer useType;


    /**
     * 备注
     **/
    @TableField("note")
    private String note;


    /**
     * 发行数量
     **/
    @TableField("publish_count")
    private Integer publishCount;


    /**
     * 已使用数量
     **/
    @TableField("use_count")
    private Integer useCount;


    /**
     * 领取数量
     **/
    @TableField("receive_count")
    private Integer receiveCount;


    /**
     * 可以领取的日期
     **/
    @TableField("enable_time")
    private String enableTime;


    /**
     * 优惠码
     **/
    @TableField("code")
    private String code;


    /**
     * 可领取的会员类型：0->无限时
     **/
    @TableField("member_level")
    private Integer memberLevel;


    /**
     * 所属店铺
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     * 门店名称
     **/
    @TableField("store_name")
    private String storeName;


    /**
     *
     **/
    @TableField("objProname")
    private String objproname;


    /**
     * 状态 0 过期  1 未过期
     **/
    @TableField("status")
    private Integer status;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否上线  0是  1否
     **/
    @TableField("if_xian")
    private Integer ifXian;
    /**
     * 发行时间
     **/
    @TableField("issue_time")
    private String issueTime;
    /**
     * 修改是否禁用 0否 1是
     **/
    @TableField("update_index")
    private Integer updateIndex;

    /**
     * 指定分类
     **/
    @TableField("good_id")
    private String goodId;
    /**
     * 指定商品
     **/
    @TableField("category_id")
    private String categoryId;
    /**
     * 订单金额
     */
    @TableField("order_price")
    private BigDecimal orderPrice;
    //商品详情
    private String goodDetailedInformation;
}
