package com.ants.base.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 刘智
 * @date 2020-08-06
 * 门店管理
 */
@Data
@TableName("sys_store")
public class SysStore implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 0 已审核 1 未审核 2 拒绝
     **/
    @TableField("status")
    private Integer status;


    /**
     * 门店名称
     **/
    @TableField("name")
    private String name;


    /**
     * 地区省
     **/
    @TableField("address_province")
    private String addressProvince;


    /**
     * 地址城市
     **/
    @TableField("address_city")
    private String addressCity;


    /**
     * 地址区域
     **/
    @TableField("address_area")
    private String addressArea;


    /**
     * 地址细节
     **/
    @TableField("address_detail")
    private String addressDetail;


    /**
     * 联系电话
     **/
    @TableField("contact_mobile")
    private String contactMobile;


    /**
     * 创建时间
     **/
    @TableField("create_time")
    private String createTime;


    /**
     * 门店电话
     **/
    @TableField("service_phone")
    private String servicePhone;


    /**
     *
     **/
    @TableField("address_lng")
    private String addressLng;


    /**
     * 地址名
     **/
    @TableField("address_lat")
    private String addressLat;


    /**
     * 所购物品计划时间
     **/
    @TableField("buy_plan_times")
    private Long buyPlanTimes;


    /**
     * 联系人名
     **/
    @TableField("contact_name")
    private String contactName;


    /**
     * 删除时间
     **/
    @TableField("delete_time")
    private Date deleteTime;


    /**
     * 自己配置文件
     **/
    @TableField("diy_profile")
    private String diyProfile;


    /**
     * 短信数量
     **/
    @TableField("sms_quantity")
    private Long smsQuantity;


    /**
     * 行业
     **/
    @TableField("industry_two")
    private Long industryTwo;


    /**
     *
     **/
    @TableField("is_star")
    private Integer isStar;


    /**
     * 尝试时间
     **/
    @TableField("try_time")
    private Date tryTime;


    /**
     * 注册类型
     **/
    @TableField("register_type")
    private Integer registerType;


    /**
     * 尝试
     **/
    @TableField("is_try")
    private Integer isTry;


    /**
     * 是否删除
     **/
    @TableField("is_deleted")
    private Integer isDeleted;


    /**
     * 到期时间
     **/
    @TableField("expire_time")
    private Date expireTime;


    /**
     * 图标
     **/
    @TableField("logo")
    private String logo;


    /**
     * 计划id
     **/
    @TableField("plan_id")
    private Long planId;


    /**
     * 支持，维持名称
     **/
    @TableField("support_name")
    private String supportName;


    /**
     *
     **/
    @TableField("uid")
    private Integer uid;


    /**
     * 门店类型 0自营 1加盟
     **/
    @TableField("type")
    private Integer type;


    /**
     * 是否选中
     **/
    @TableField("is_checked")
    private Integer isChecked;


    /**
     * 联系QQ
     **/
    @TableField("contact_qq")
    private String contactQq;


    /**
     *
     **/
    @TableField("last_login_time")
    private Date lastLoginTime;


    /**
     * 支持电话
     **/
    @TableField("support_phone")
    private String supportPhone;


    /**
     *
     **/
    @TableField("contact_qrcode")
    private String contactQrcode;


    /**
     * 描述
     **/
    @TableField("description")
    private String description;


    /**
     * 行业1
     **/
    @TableField("industry_one")
    private Long industryOne;


    /**
     *
     **/
    @TableField("collect")
    private Integer collect;


    /**
     *
     **/
    @TableField("hit")
    private Integer hit;


    /**
     *
     **/
    @TableField("goods_count")
    private Integer goodsCount;


    /**
     *
     **/
    @TableField("member_count")
    private Integer memberCount;


    /**
     *
     **/
    @TableField("order_count")
    private Integer orderCount;


    /**
     *
     **/
    @TableField("pay_amount")
    private BigDecimal payAmount;


    /**
     *
     **/
    @TableField("article_count")
    private Integer articleCount;


    /**
     * 精品店铺标识,0:否，1:是
     **/
    @TableField("is_boutique")
    private Integer isBoutique;


    /**
     *
     **/
    @TableField("amount")
    private BigDecimal amount;


    /**
     *
     **/
    @TableField("freez_amount")
    private BigDecimal freezAmount;


    /**
     * 门店面积
     **/
    @TableField("name_area")
    private String nameArea;


    /**
     * 门店人数
     **/
    @TableField("name_number")
    private Integer nameNumber;


    /**
     * 门店地址
     **/
    @TableField("name_address")
    private String nameAddress;


    /**
     * 商户id
     **/
    @TableField("business_id")
    private String businessId;


    /**
     * 权限id
     **/
    @TableField("role_id")
    private Integer roleId;


    /**
     * 所属门店
     **/
    @TableField("belonging_store")
    private Integer belongingStore;


    /**
     * 是否服务类型 0没 1有
     **/
    @TableField("if_service")
    private Integer ifService;


    /**
     * 权限名称
     **/
    @TableField("role_name")
    private String roleName;
    /**
     * 会员中对应的卡号
     **/
    @TableField("code")
    private String code;


    @TableField(exist = false)
    private List<PmsProductAttributeCategory> list;


}
