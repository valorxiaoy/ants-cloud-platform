package com.ants.module.store;

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
public class SysStoreDto implements Serializable {

    private Integer id;

    /**
     * 0 已审核 1 未审核 2 拒绝
     **/
    private Integer status;

    /**
     * 门店名称
     **/
    private String name;

    /**
     * 地区省
     **/
    private String addressProvince;

    /**
     * 地址城市
     **/
    private String addressCity;

    /**
     * 地址区域
     **/
    private String addressArea;

    /**
     * 地址细节
     **/
    private String addressDetail;

    /**
     * 联系电话
     **/
    private String contactMobile;

    /**
     * 创建时间
     **/
    private String createTime;

    /**
     * 门店电话
     **/
    private String servicePhone;

    /**
     *
     **/
    private String addressLng;


    /**
     * 地址名
     **/
    private String addressLat;

    /**
     * 所购物品计划时间
     **/
    private Long buyPlanTimes;

    /**
     * 联系人名
     **/
    private String contactName;

    /**
     * 删除时间
     **/
    private Date deleteTime;

    /**
     * 自己配置文件
     **/
    private String diyProfile;

    /**
     * 短信数量
     **/
    private Long smsQuantity;

    /**
     * 行业
     **/
    private Long industryTwo;

    /**
     *
     **/
    private Integer isStar;

    /**
     * 尝试时间
     **/
    private Date tryTime;

    /**
     * 注册类型
     **/
    private Integer registerType;

    /**
     * 尝试
     **/
    private Integer isTry;

    /**
     * 是否删除
     **/
    private Integer isDeleted;

    /**
     * 到期时间
     **/
    private Date expireTime;

    /**
     * 图标
     **/
    private String logo;

    /**
     * 计划id
     **/
    private Long planId;

    /**
     * 支持，维持名称
     **/
    private String supportName;

    /**
     *
     **/
    private Integer uid;

    /**
     * 门店类型 0自营 1加盟
     **/
    private Integer type;

    /**
     * 是否选中
     **/
    private Integer isChecked;

    /**
     * 联系QQ
     **/
    private String contactQq;

    /**
     *
     **/
    private Date lastLoginTime;

    /**
     * 支持电话
     **/
    private String supportPhone;

    /**
     *
     **/
    private String contactQrcode;

    /**
     * 描述
     **/
    private String description;

    /**
     * 行业1
     **/
    private Long industryOne;

    /**
     *
     **/
    private Integer collect;

    /**
     *
     **/
    private Integer hit;

    /**
     *
     **/
    private Integer goodsCount;

    /**
     *
     **/
    private Integer memberCount;

    /**
     *
     **/
    private Integer orderCount;

    /**
     *
     **/
    private BigDecimal payAmount;

    /**
     *
     **/
    private Integer articleCount;

    /**
     * 精品店铺标识,0:否，1:是
     **/
    private Integer isBoutique;

    /**
     *
     **/
    private BigDecimal amount;

    /**
     *
     **/
    private BigDecimal freezAmount;

    /**
     * 门店面积
     **/
    private String nameArea;

    /**
     * 门店人数
     **/
    private Integer nameNumber;

    /**
     * 门店地址
     **/
    private String nameAddress;

    /**
     * 商户id
     **/
    private String businessId;

    /**
     * 权限id
     **/
    private Integer roleId;

    /**
     * 所属门店
     **/
    private Integer belongingStore;

    /**
     * 是否服务类型 0没 1有
     **/
    private Integer ifService;

    /**
     * 权限名称
     **/
    private String roleName;

    /**
     * 会员中对应的卡号
     **/
    private String code;

    private List<PmsProductAttributeCategoryDto> list;

}
