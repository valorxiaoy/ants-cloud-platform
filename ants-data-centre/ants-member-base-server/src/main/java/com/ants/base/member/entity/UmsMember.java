package com.ants.base.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaomi
 * @date 2020-07-28
 * 会员列表
 */
@Data
@TableName("ums_member")
public class UmsMember implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 会员等级id
     **/
    @TableField("member_level_id")
    private Long memberLevelId;


    /**
     * 用户名
     **/
    @TableField("username")
    private String username;


    /**
     * 密码
     **/
    @TableField("password")
    private String password;


    /**
     * 昵称
     **/
    @TableField("nickname")
    private String nickname;


    /**
     * 手机号码
     **/
    @TableField("phone")
    private String phone;


    /**
     * 帐号启用状态:0->禁用；1->启用
     **/
    @TableField("status")
    private Integer status;


    /**
     * 注册时间
     **/
    @TableField("create_time")
    private String createTime;


    /**
     * 头像
     **/
    @TableField("icon")
    private String icon;


    /**
     * 性别：0->未知；1->男；2->女
     **/
    @TableField("gender")
    private Integer gender;


    /**
     * 生日
     **/
    @TableField("birthday")
    private Date birthday;


    /**
     * 所在城市
     **/
    @TableField("city")
    private String city;


    /**
     * 职业
     **/
    @TableField("job")
    private String job;


    /**
     * 个性签名
     **/
    @TableField("personalized_signature")
    private String personalizedSignature;


    /**
     * 用户来源 1 小程序 2 公众号 3 页面
     **/
    @TableField("source_type")
    private Integer sourceType;


    /**
     * 赠送积分
     **/
    @TableField("integration")
    private Integer integration;


    /**
     * 成长值
     **/
    @TableField("growth")
    private Integer growth;


    /**
     * 剩余抽奖次数
     **/
    @TableField("luckey_count")
    private Integer luckeyCount;


    /**
     * 历史积分数量
     **/
    @TableField("history_integration")
    private Integer historyIntegration;


    /**
     *
     **/
    @TableField("avatar")
    private String avatar;


    /**
     *
     **/
    @TableField("weixin_openid")
    private String weixinOpenid;


    /**
     *
     **/
    @TableField("invitecode")
    private String invitecode;


    /**
     * 余额
     **/
    @TableField("blance")
    private BigDecimal blance;


    /**
     *
     **/
    @TableField("school_id")
    private Long schoolId;


    /**
     * 所属店铺
     **/
    @TableField("store_id")
    private Integer storeId;


    /**
     *
     **/
    @TableField("area_id")
    private Long areaId;


    /**
     *
     **/
    @TableField("school_name")
    private String schoolName;


    /**
     *
     **/
    @TableField("area_name")
    private String areaName;


    /**
     *
     **/
    @TableField("buy_count")
    private Integer buyCount;


    /**
     *
     **/
    @TableField("buy_money")
    private BigDecimal buyMoney;


    /**
     *
     **/
    @TableField("member_level_name")
    private String memberLevelName;


    /**
     *
     **/
    @TableField("room_nums")
    private String roomNums;


    /**
     *
     **/
    @TableField("room_desc")
    private String roomDesc;


    /**
     * QQ号
     **/
    @TableField("qq_number")
    private String qqNumber;


    /**
     * 地址
     **/
    @TableField("address")
    private String address;


    /**
     * 第一个宝宝名称
     **/
    @TableField("bone_name")
    private String boneName;


    /**
     * 第一个宝宝性别 性别：0->未知；1->男；2->女
     **/
    @TableField("bone_sex")
    private Integer boneSex;


    /**
     * 第一个宝宝生日
     **/
    @TableField("bone_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date boneBirthday;


    /**
     * 第一个宝宝喂养方式
     **/
    @TableField("bone_type")
    private Integer boneType;


    /**
     * 第一个宝宝与会员关系
     **/
    @TableField("bone_relationship")
    private Integer boneRelationship;


    /**
     * 第二个宝宝名称
     **/
    @TableField("btwo_name")
    private String btwoName;


    /**
     * 第二个宝宝性别 性别  0未知   1男   2女
     **/
    @TableField("btwo_sex")
    private Integer btwoSex;


    /**
     * 第二个宝宝生日
     **/
    @TableField("btwo_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date btwoBirthday;


    /**
     * 第二个宝宝喂养方式
     **/
    @TableField("btwo_type")
    private Integer btwoType;


    /**
     * 第二个宝宝与会员关系
     **/
    @TableField("btwo_relationship")
    private Integer btwoRelationship;


    /**
     * 第三个宝宝名称
     **/
    @TableField("bthree_name")
    private String bthreeName;


    /**
     * 第三个宝宝性别 0 未知  1男   2女
     **/
    @TableField("bthree_sex")
    private Integer bthreeSex;


    /**
     * 第三个宝宝的喂养方式
     **/
    @TableField("bthree_type")
    private Integer bthreeType;


    /**
     * 第三个宝宝与会员关系
     **/
    @TableField("bthree_relationship")
    private Integer bthreeRelationship;


    /**
     * 第四个宝宝名称
     **/
    @TableField("bfour_name")
    private String bfourName;


    /**
     * 第四个宝宝的性别 0  未知  1男  2女
     **/
    @TableField("bfour_sex")
    private Integer bfourSex;


    /**
     * 第四个宝宝的生日
     **/
    @TableField("bfour_birthday")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date bfourBirthday;


    /**
     * 第四个宝宝的喂养方式
     **/
    @TableField("bfour_type")
    private Integer bfourType;


    /**
     * 第四个宝宝与会员关系
     **/
    @TableField("bfour_relationship")
    private Integer bfourRelationship;


    /**
     * 第三个宝宝的生日
     **/
    @TableField("bthree_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date bthreeBirthday;


    /**
     * 备注
     **/
    @TableField("note")
    private String note;


    /**
     * 归属导购ID
     **/
    @TableField("user_id")
    private Integer userId;

    /**
     * 会员卡号
     **/
    @TableField("member_code")
    private String memberCode;
    /**
     * 会员卡号
     **/
    @TableField("open_id")
    private String openId;

    /**
     * 可用积分
     */
    @TableField("available_integral")
    private Integer availableIntegral;

    /**
     * 怀孕时间
     */
    @TableField("mom_cycle")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date momCycle;
}
