package com.ants.module.member;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaomi
 * @date 2020-07-28
 * 会员列表
 */
@Data
public class UmsMemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 会员等级id
     **/
    private Long memberLevelId;

    /**
     * 用户名
     **/
    private String username;

    /**
     * 密码
     **/
    private String password;

    /**
     * 昵称
     **/
    private String nickname;

    /**
     * 手机号码
     **/
    private String phone;

    /**
     * 帐号启用状态:0->禁用；1->启用
     **/
    private Integer status;

    /**
     * 注册时间
     **/
    private String createTime;

    /**
     * 头像
     **/
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     **/
    private Integer gender;

    /**
     * 生日
     **/
    private Date birthday;

    /**
     * 所在城市
     **/
    private String city;

    /**
     * 职业
     **/
    private String job;

    /**
     * 个性签名
     **/
    private String personalizedSignature;

    /**
     * 用户来源 1 小程序 2 公众号 3 页面
     **/
    private Integer sourceType;

    /**
     * 赠送积分
     **/
    private Integer integration;

    /**
     * 成长值
     **/
    private Integer growth;

    /**
     * 剩余抽奖次数
     **/
    private Integer luckeyCount;

    /**
     * 历史积分数量
     **/
    private Integer historyIntegration;

    /**
     *
     **/
    private String avatar;

    /**
     *
     **/
    private String weixinOpenid;

    /**
     *
     **/
    private String invitecode;

    /**
     * 余额
     **/
    private BigDecimal blance;

    /**
     *
     **/
    private Long schoolId;

    /**
     * 所属店铺
     **/
    private Integer storeId;

    /**
     *
     **/
    private Long areaId;

    /**
     *
     **/
    private String schoolName;

    /**
     *
     **/
    private String areaName;

    /**
     *
     **/
    private Integer buyCount;

    /**
     *
     **/
    private BigDecimal buyMoney;

    /**
     *
     **/
    private String memberLevelName;

    /**
     *
     **/
    private String roomNums;

    /**
     *
     **/
    private String roomDesc;

    /**
     * QQ号
     **/
    private String qqNumber;

    /**
     * 地址
     **/
    private String address;

    /**
     * 第一个宝宝名称
     **/
    private String boneName;

    /**
     * 第一个宝宝性别 性别：0->未知；1->男；2->女
     **/
    private Integer boneSex;

    /**
     * 第一个宝宝生日
     **/
    private Date boneBirthday;

    /**
     * 第一个宝宝喂养方式
     **/
    private Integer boneType;

    /**
     * 第一个宝宝与会员关系
     **/
    private Integer boneRelationship;

    /**
     * 第二个宝宝名称
     **/
    private String btwoName;

    /**
     * 第二个宝宝性别 性别  0未知   1男   2女
     **/
    private Integer btwoSex;

    /**
     * 第二个宝宝生日
     **/
    private Date btwoBirthday;

    /**
     * 第二个宝宝喂养方式
     **/
    private Integer btwoType;

    /**
     * 第二个宝宝与会员关系
     **/
    private Integer btwoRelationship;

    /**
     * 第三个宝宝名称
     **/
    private String bthreeName;

    /**
     * 第三个宝宝性别 0 未知  1男   2女
     **/
    private Integer bthreeSex;

    /**
     * 第三个宝宝的喂养方式
     **/
    private Integer bthreeType;

    /**
     * 第三个宝宝与会员关系
     **/
    private Integer bthreeRelationship;

    /**
     * 第四个宝宝名称
     **/
    private String bfourName;

    /**
     * 第四个宝宝的性别 0  未知  1男  2女
     **/
    private Integer bfourSex;

    /**
     * 第四个宝宝的生日
     **/
    private Date bfourBirthday;

    /**
     * 第四个宝宝的喂养方式
     **/
    private Integer bfourType;

    /**
     * 第四个宝宝与会员关系
     **/
    private Integer bfourRelationship;

    /**
     * 第三个宝宝的生日
     **/
    private Date bthreeBirthday;

    /**
     * 备注
     **/
    private String note;

    /**
     * 归属导购ID
     **/
    private Integer userId;

    /**
     * 会员卡号
     **/
    private String memberCode;

    /**
     * 会员卡号
     **/
    private String openId;

    /**
     * 可用积分
     */
    private Integer availableIntegral;

    /**
     * 怀孕时间
     */
    private Date momCycle;
}
