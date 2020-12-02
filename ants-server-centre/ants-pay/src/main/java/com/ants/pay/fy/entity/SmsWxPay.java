package com.ants.pay.fy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author whd
 * @date 2020/11/28
 */
@Data
@TableName("sys_wxpay_config")
public class SmsWxPay {
    @TableId("id")
    private Integer id;

    @TableField("app_id")
    private String appId;

    @TableField("app_secret")
    private String appSecret;

    @TableField("api_key")
    private String apiKey;

    @TableField("mch_id")
    private String mchId;

    @TableField("notify_url")
    private String notifyUrl;

    @TableField("order_query")
    private String orderQuery;

    @TableField("gate_url")
    private String gateUrl;

    @TableField("token")
    private String token;

    @TableField("store_id")
    private String storeId;

}
