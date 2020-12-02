package com.ants.module.integration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘智
 * @description: 积分规则类
 * @date 2020-08-15
 * 积分规则
 */
@Data
public class IntegralRuleDto implements Serializable {


    private Integer id;


    /**
     * 积分规则名称
     **/
    private String name;


    /**
     * 会员等级
     **/
    private Integer member;


    /**
     * 积分方式
     **/
    private Integer method;
    /**
     * 等级方式标识
     */
    private String code;


    /**
     * 消费金额
     **/
    private BigDecimal money;


    /**
     * 消费次数
     **/
    private Integer consumptionNumber;


    /**
     * 商品
     **/
    private String commodity;


    /**
     * 存储商品类别
     **/
    private String categoryIds;
    private List<IntegralDetailsDto> categoryIdsList;

    public List<IntegralDetailsDto> getCategoryIdsList() {
        if (categoryIdsList != null) {
            return this.categoryIdsList;
        }
        if (categoryIds == null || "".equals(categoryIds)) {
            return new ArrayList<>();
        }
        JSONArray objects = JSONArray.parseArray(categoryIds);
        return objects.toJavaList(IntegralDetailsDto.class);
    }

    /**
     * 商品品牌
     **/
    private String brandIds;

    public String getBrandIds() {
        if (brandIdsList != null) {
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(brandIdsList));
            return array.toJSONString();
        }
        return "";
    }

    private List<IntegralDetailsDto> brandIdsList;

    public List<IntegralDetailsDto> getBrandIdsList() {
        if (brandIds == null || "".equals(brandIds)) {
            return new ArrayList<>();
        }
        JSONArray objects = JSONArray.parseArray(brandIds);
        return objects.toJavaList(IntegralDetailsDto.class);
    }

    /**
     * 商品等级
     **/
    private String gradeIds;
    private List<IntegralDetailsDto> gradeIdsList;

    public List<IntegralDetailsDto> getGradeIdsList() {
        if (gradeIds == null || "".equals(gradeIds)) {
            return new ArrayList<>();
        }
        JSONArray objects = JSONArray.parseArray(gradeIds);
        return objects.toJavaList(IntegralDetailsDto.class);
    }


    /**
     * 积分
     **/
    private Integer integral;

    /**
     * 新会员积分
     **/
    private Integer newMember;

    /**
     * 老会员积分
     **/
    private Integer oldMember;

    /**
     * 老会员带新会员比例
     **/
    private Integer integralProportion;

    /**
     * 是否生日当天启用 0（启用）1（不启用）
     **/
    private Integer birthdayEnable;

    /**
     * 是否生日当月启用 0（启用）1（不启用）
     **/
    private Integer birthdaymonthEnable;

    /**
     * 每天是否启用 0（启用）1（不启用）
     **/
    private Integer everydayEnable;

    /**
     * 每周是否启用 0（启用）1（不启用）
     **/
    private Integer weeklyEnable;

    /**
     * 每月是否启用 0（启用）1（不启用）
     **/
    private Integer monthlyEnable;

    /**
     * 特定节日是否启用 0（启用）1（不启用）
     **/
    private Integer appointeddayEnable;

    /**
     * 特定节假日积分开始时间
     **/
    private Date specialDayStart;

    /**
     * 特定节假日积分结束时间
     **/
    private Date specialDayEnd;

    /**
     * 生日当天倍数
     **/
    private Double birthdayMultiple;

    /**
     * 生日当月倍数
     **/
    private Double birthdaymonthMultiple;

    /**
     * 每天倍数
     **/
    private Double everydayMultiple;

    /**
     * 每周倍数
     **/
    private Double weeklyMultiple;

    /**
     * 每月倍数
     **/
    private Double monthlyMultiple;

    /**
     * 特定节日倍数
     **/
    private Double appointeddayMultiple;

    /**
     * 每天开始时间
     **/
    private String dayStarttime;

    /**
     * 每天结束时间
     **/
    private String dayEndtime;

    /**
     * 每周时间（例：星期一）
     **/
    private String weekday;

    /**
     * 每月指定时间(日) 例：1,2,3 逗号隔开
     **/
    private String month;

    /**
     * 消费特价商品积分是否启用 （0） 启用 （1）不启用
     **/
    private Integer discountEnable;

    /**
     * 特价商品积分是否启用 （0） 启用 （1）不启用
     **/
    private Integer specialEnable;

    /**
     * 每单按照整数累加积分（0） 启用 （1）不启用
     **/
    private Integer accumulationEnable;

    /**
     * 积分付款金额不再积分 （0） 启用 （1）不启用
     **/
    private Integer pointpaymentEnable;

    /**
     * 子卡兑换父卡积分比例 (%)
     **/
    private Integer proportion;

    /**
     * 是否启用积分付款 （0） 启用 （1）不启用
     **/
    private Integer paymentEnable;

    /**
     * 积分付款满多少分
     **/
    private Integer integralF;

    /**
     * 积分付款抵扣多少元
     **/
    private Double integralMoney;

    /**
     * 子卡开户新卡积分
     **/
    private Integer newcardIntegral;

    /**
     * 积分换钱的积分
     **/
    private Double changeIntegral;


    /**
     * 分类规则id
     **/
    private Integer ruleDetalisId;

    /**
     * 积分换钱的金额
     **/
    private Double changeAmount;

    /**
     * 商户id
     **/
    private Integer storeId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 会员等级名称
     */
    private String levelName;

    /**
     * 计分方式名称
     */
    private String integralName;

    /**
     * 是否删除
     */
    private Integer isDelete;
}
