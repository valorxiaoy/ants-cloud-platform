package com.ants.base.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaomi
 * @date 2020-07-28
 * 会员等级设置
 */
@Data
@TableName("ums_member_level")
public class UmsMemberLevel implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 会员等级名称
     **/
    @TableField("name")
    private String name;


    /**
     *
     **/
    @TableField("growth_point")
    private Integer growthPoint;


    /**
     * 是否为默认等级：0->不是；1->是
     **/
    @TableField("default_status")
    private Integer defaultStatus;


    /**
     * 免运费标准
     **/
    @TableField("free_freight_point")
    private BigDecimal freeFreightPoint;


    /**
     * 每次评价获取的成长值
     **/
    @TableField("comment_growth_point")
    private Integer commentGrowthPoint;


    /**
     * 是否有免邮特权，0否，1是
     **/
    @TableField("priviledge_free_freight")
    private Integer priviledgeFreeFreight;


    /**
     * 是否有签到特权，0否，1是
     **/
    @TableField("priviledge_sign_in")
    private Integer priviledgeSignIn;


    /**
     * 是否有评论获奖励特权，0否，1是
     **/
    @TableField("priviledge_comment")
    private Integer priviledgeComment;


    /**
     * 是否有专享活动特权，0否，1是
     **/
    @TableField("priviledge_promotion")
    private Integer priviledgePromotion;


    /**
     * 是否有会员价格特权，0否，1是
     **/
    @TableField("priviledge_member_price")
    private Integer priviledgeMemberPrice;


    /**
     * 是否有生日特权，0否，1是
     **/
    @TableField("priviledge_birthday")
    private Integer priviledgeBirthday;


    /**
     * 可发文章数
     **/
    @TableField("articlecount")
    private Integer articlecount;


    /**
     * 可发商品数
     **/
    @TableField("goodscount")
    private Integer goodscount;


    /**
     * 成为会员的价格
     **/
    @TableField("price")
    private BigDecimal price;


    /**
     *
     **/
    @TableField("note")
    private String note;


    /**
     * 所属店铺
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     *
     **/
    @TableField("icon")
    private String icon;


    /**
     *
     **/
    @TableField("pic")
    private String pic;


    /**
     * 类别编码
     **/
    @TableField("memberid")
    private String memberid;


    /**
     * 类别名称
     **/
    @TableField("membername")
    private String membername;


    /**
     * 优惠方式  0系统  1自定义
     **/
    @TableField("membermode")
    private Integer membermode;


    /**
     * 会员折扣率
     **/
    @TableField("memberdisrate")
    private String memberdisrate;


    /**
     * 是否积分，0否，1是
     **/
    @TableField("memberjifen")
    private Integer memberjifen;


    /**
     * 是否储值，0否，1是
     **/
    @TableField("memberchuzhi")
    private Integer memberchuzhi;


    /**
     * 是否寄存，0否，1是
     **/
    @TableField("member_deposit")
    private Integer memberDeposit;


    /**
     * 下一级编码
     **/
    @TableField("xyjbm")
    private String xyjbm;


    /**
     * 升级规则 0手动升级。1自动升级，2消费金满减，3积分满
     **/
    @TableField("upgrade_rules")
    private Integer upgradeRules;


    /**
     * 升级规则对应的值id
     **/
    @TableField("rules_value")
    private Integer rulesValue;

    /**
     * 升级规则对应的值name
     **/
    @TableField("rules_name")
    private String rulesName;
    /**
     * 会员优惠方式对应的值  0系统
     **/
    @TableField("mode_value")
    private String modeValue;

    /**
     * 会员优惠方式对应的值   1自定义
     **/
    @TableField("mode_value1")
    private String modeValue1;
    /**
     * 创建时间
     **/
    @TableField("create_time")
    private Date createTime;

    //，0消费金满减，1积分满
    @TableField("upgrade_rules_id")
    private Integer upgradeRulesId;
    //自动升级0消费金满减对应的值
    @TableField("upgrade_rules_value")
    private String upgradeRulesValue;

    //自动升级1积分满对应的值
    @TableField("upgrade_rules_value1")
    private String upgradeRulesValue1;
    //升级规则对应的会员名称
    @TableField("upgrade_rules_name")
    private String upgradeRulesName;
    //初始化 是否第一次建等级 0否 1是
    @TableField("initial_value")
    private Integer initialValue;
}
