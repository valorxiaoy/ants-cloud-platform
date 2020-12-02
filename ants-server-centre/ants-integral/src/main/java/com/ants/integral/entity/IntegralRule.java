package com.ants.integral.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ants.module.integration.IntegralDetailsDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘智
 * @date 2020-08-15
 * 积分规则
 */
@Data
@TableName("ums_integral_rule")
public class IntegralRule implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 积分规则名称
     **/
    @TableField("name")
    private String name;


    /**
     * 会员等级
     **/
    @TableField("member")
    private Integer member;


    /**
     * 积分方式
     **/
    @TableField("method")
    private Integer method;
    /**
     * 等级方式标识
     */
    @TableField("code")
    private String code;


    /**
     * 消费金额
     **/
    @TableField("money")
    private BigDecimal money;


    /**
     * 消费次数
     **/
    @TableField("consumption_number")
    private Integer consumptionNumber;


    /**
     * 商品
     **/
    @TableField("commodity")
    private String commodity;


    /**
     * 存储商品类别
     **/
    @TableField("category_ids")
    private String categoryIds;
    @TableField(exist = false)
    private List<IntegralDetailsDto> categoryIdsList;

    /*public List<IntegralDetailsDto> getCategoryIdsList() {
     *//*if (categoryIds == null) {
            categoryIdsList = new ArrayList<>();
            return categoryIdsList;
        }*//*
        if (categoryIdsList != null) {
            return this.categoryIdsList;
        }
        if (categoryIds == null || "".equals(categoryIds)) {
            List<IntegralDetailsDto> list = new ArrayList<>();
            return list;
        }
        JSONArray objects = JSONArray.parseArray(categoryIds);
        List<UmsIntegralRuleMethodDto> umsIntegralRuleMethodDtos = objects.toJavaList(UmsIntegralRuleMethodDto.class);
        return umsIntegralRuleMethodDtos;
    }*/

    /**
     * 商品品牌
     **/
    @TableField("brand_ids")
    private String brandIds;

   /* public String getBrandIds() {
        if (brandIdsList != null) {
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(brandIdsList));
            return array.toJSONString();
        }
        return "";
    }

    @TableField(exist = false)
    private List<UmsIntegralRuleMethodDto> brandIdsList;

    public List<UmsIntegralRuleMethodDto> getBrandIdsList() {
        if (brandIds == null || "".equals(brandIds)) {
            List<UmsIntegralRuleMethodDto> list = new ArrayList<>();
            return list;
        }
        JSONArray objects = JSONArray.parseArray(brandIds);
        List<UmsIntegralRuleMethodDto> umsIntegralRuleMethodDtos = objects.toJavaList(UmsIntegralRuleMethodDto.class);
        return umsIntegralRuleMethodDtos;
    }*/


    /**
     * 商品等级
     **/
    @TableField("grade_ids")
    private String gradeIds;
    /**
     * 商品等级id 集合
     */
    @TableField(exist = false)
    private List<IntegralDetailsDto> gradeIdsList;

    /*public List<UmsIntegralRuleMethodDto> getGradeIdsList() {
        if (gradeIds == null || "".equals(gradeIds)) {
            List<UmsIntegralRuleMethodDto> list = new ArrayList<>();
            return list;
        }
        JSONArray objects = JSONArray.parseArray(gradeIds);
        List<UmsIntegralRuleMethodDto> umsIntegralRuleMethodDtos = objects.toJavaList(UmsIntegralRuleMethodDto.class);
        return umsIntegralRuleMethodDtos;
    }*/


    /**
     * 积分
     **/
    @TableField("integral")
    private Integer integral;


    /**
     * 新会员积分
     **/
    @TableField("new_member")
    private Integer newMember;


    /**
     * 老会员积分
     **/
    @TableField("old_member")
    private Integer oldMember;


    /**
     * 老会员带新会员比例
     **/
    @TableField("integral_proportion")
    private Integer integralProportion;


    /**
     * 是否生日当天启用 0（启用）1（不启用）
     **/
    @TableField("birthday_enable")
    private Integer birthdayEnable;


    /**
     * 是否生日当月启用 0（启用）1（不启用）
     **/
    @TableField("birthdaymonth_enable")
    private Integer birthdaymonthEnable;


    /**
     * 每天是否启用 0（启用）1（不启用）
     **/
    @TableField("everyday_enable")
    private Integer everydayEnable;


    /**
     * 每周是否启用 0（启用）1（不启用）
     **/
    @TableField("weekly_enable")
    private Integer weeklyEnable;


    /**
     * 每月是否启用 0（启用）1（不启用）
     **/
    @TableField("monthly_enable")
    private Integer monthlyEnable;


    /**
     * 特定节日是否启用 0（启用）1（不启用）
     **/
    @TableField("appointedday_enable")
    private Integer appointeddayEnable;


    /**
     * 特定节假日积分开始时间
     **/
    @TableField("special_day_start")
    private Date specialDayStart;


    /**
     * 特定节假日积分结束时间
     **/
    @TableField("special_day_end")
    private Date specialDayEnd;


    /**
     * 生日当天倍数
     **/
    @TableField("birthday_multiple")
    private Double birthdayMultiple;


    /**
     * 生日当月倍数
     **/
    @TableField("birthdaymonth_multiple")
    private Double birthdaymonthMultiple;


    /**
     * 每天倍数
     **/
    @TableField("everyday_multiple")
    private Double everydayMultiple;


    /**
     * 每周倍数
     **/
    @TableField("weekly_multiple")
    private Double weeklyMultiple;


    /**
     * 每月倍数
     **/
    @TableField("monthly_multiple")
    private Double monthlyMultiple;


    /**
     * 特定节日倍数
     **/
    @TableField("appointedday_multiple")
    private Double appointeddayMultiple;


    /**
     * 每天开始时间
     **/
    @TableField("day_starttime")
    private String dayStarttime;


    /**
     * 每天结束时间
     **/
    @TableField("day_endtime")
    private String dayEndtime;


    /**
     * 每周时间（例：星期一）
     **/
    @TableField("weekday")
    private String weekday;


    /**
     * 每月指定时间(日) 例：1,2,3 逗号隔开
     **/
    @TableField("month")
    private String month;


    /**
     * 消费特价商品积分是否启用 （0） 启用 （1）不启用
     **/
    @TableField("Discount_enable")
    private Integer discountEnable;


    /**
     * 特价商品积分是否启用 （0） 启用 （1）不启用
     **/
    @TableField("Special_enable")
    private Integer specialEnable;


    /**
     * 每单按照整数累加积分（0） 启用 （1）不启用
     **/
    @TableField("accumulation_enable")
    private Integer accumulationEnable;


    /**
     * 积分付款金额不再积分 （0） 启用 （1）不启用
     **/
    @TableField("pointpayment_enable")
    private Integer pointpaymentEnable;


    /**
     * 子卡兑换父卡积分比例 (%)
     **/
    @TableField("proportion")
    private Integer proportion;


    /**
     * 是否启用积分付款 （0） 启用 （1）不启用
     **/
    @TableField("payment_enable")
    private Integer paymentEnable;


    /**
     * 积分付款满多少分
     **/
    @TableField("integral_f")
    private Integer integralF;


    /**
     * 积分付款抵扣多少元
     **/
    @TableField("integral_money")
    private Double integralMoney;


    /**
     * 子卡开户新卡积分
     **/
    @TableField("newcard_integral")
    private Integer newcardIntegral;


    /**
     * 积分换钱的积分
     **/
    @TableField("change_integral")
    private Double changeIntegral;


    /**
     * 分类规则id
     **/
    @TableField("rule_detalis_id")
    private Integer ruleDetalisId;


    /**
     * 积分换钱的金额
     **/
    @TableField("change_amount")
    private Double changeAmount;


    /**
     * 商户id
     **/
    @TableField("store_id")
    private Integer storeId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    @TableField(exist = false)
    private String levelName;

    @TableField(exist = false)
    private String integralName;
    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;
}
