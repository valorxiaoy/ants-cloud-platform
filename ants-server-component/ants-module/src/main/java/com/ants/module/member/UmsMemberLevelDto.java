package com.ants.module.member;

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
public class UmsMemberLevelDto implements Serializable {

    private Long id;

    /**
     * 会员等级名称
     **/
    private String name;

    /**
     *
     **/
    private Integer growthPoint;

    /**
     * 是否为默认等级：0->不是；1->是
     **/
    private Integer defaultStatus;

    /**
     * 免运费标准
     **/
    private BigDecimal freeFreightPoint;

    /**
     * 每次评价获取的成长值
     **/
    private Integer commentGrowthPoint;

    /**
     * 是否有免邮特权，0否，1是
     **/
    private Integer priviledgeFreeFreight;

    /**
     * 是否有签到特权，0否，1是
     **/
    private Integer priviledgeSignIn;

    /**
     * 是否有评论获奖励特权，0否，1是
     **/
    private Integer priviledgeComment;

    /**
     * 是否有专享活动特权，0否，1是
     **/
    private Integer priviledgePromotion;

    /**
     * 是否有会员价格特权，0否，1是
     **/
    private Integer priviledgeMemberPrice;

    /**
     * 是否有生日特权，0否，1是
     **/
    private Integer priviledgeBirthday;

    /**
     * 可发文章数
     **/
    private Integer articlecount;

    /**
     * 可发商品数
     **/
    private Integer goodscount;

    /**
     * 成为会员的价格
     **/
    private BigDecimal price;

    /**
     *
     **/
    private String note;

    /**
     * 所属店铺
     **/
    private Integer storeId;

    /**
     *
     **/
    private String icon;

    /**
     *
     **/
    private String pic;

    /**
     * 类别编码
     **/
    private String memberid;

    /**
     * 类别名称
     **/
    private String membername;

    /**
     * 优惠方式  0系统  1自定义
     **/
    private Integer membermode;

    /**
     * 会员折扣率
     **/
    private String memberdisrate;

    /**
     * 是否积分，0否，1是
     **/
    private Integer memberjifen;

    /**
     * 是否储值，0否，1是
     **/
    private Integer memberchuzhi;

    /**
     * 是否寄存，0否，1是
     **/
    private Integer memberDeposit;

    /**
     * 下一级编码
     **/
    private String xyjbm;

    /**
     * 升级规则 0手动升级。1自动升级，2消费金满减，3积分满
     **/
    private Integer upgradeRules;

    /**
     * 升级规则对应的值id
     **/
    private Integer rulesValue;

    /**
     * 升级规则对应的值name
     **/
    private String rulesName;

    /**
     * 会员优惠方式对应的值  0系统
     **/
    private String modeValue;

    /**
     * 会员优惠方式对应的值   1自定义
     **/
    private String modeValue1;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 0消费金满减，1积分满
     */
    private Integer upgradeRulesId;

    /**
     * 自动升级0消费金满减对应的值
     */
    private String upgradeRulesValue;

    /**
     * 自动升级1积分满对应的值
     */
    private String upgradeRulesValue1;

    /**
     * 升级规则对应的会员名称
     */
    private String upgradeRulesName;

    /**
     * 初始化 是否第一次建等级 0否 1是
     */
    private Integer initialValue;
}
