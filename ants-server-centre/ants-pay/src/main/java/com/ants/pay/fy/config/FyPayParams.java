package com.ants.pay.fy.config;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 富有支付配置参数
 *
 * @author Yueyang
 * @create 2020-12-02 7:22
 **/
public class FyPayParams {

    /**
     * 支付结果回调地址
     */
    public final static String ORDER_NOTIFY_URL = "http://47.108.128.208:8089/fuiou/pay/notify";

    /**
     * 接口版本号
     */
    public final static String FY_VERSION = "1.0";

    /**
     * 富有机构号
     */
    public final static String FY_INS_CD = "08K0021776";

    /**
     * 货币类型,默认人民币：CNY
     */
    public final static String FY_CURR_TYPE = "CNY";

    /**
     * 终端号(没有真实终端号统一填88888888)
     */
    public final static String FY_TERM_ID = "88888888";

    /**
     * 终端IP
     */
    public final static String FY_TERM_IP = "127.0.0.1";

    /**
     * 必填 订单类型:
     * JSAPI--公众号支付
     * FWC--支付宝服务窗、支付宝小程序
     * LETPAY-微信小程序
     * BESTPAY--翼支付js
     */
    public final static String FY_ORDER_TYPE_JSAPI = "JSAPI";
    public final static String FY_ORDER_TYPE_FWC = "FWC";
    public final static String FY_ORDER_TYPE_LETPAY = "LETPAY";
    public final static String FY_ORDER_TYPE_BESTPAY = "BESTPAY";

    public static BigDecimal getAmount(BigDecimal amount) {
        // 单位分
        amount = amount.multiply(new BigDecimal(100));
        //取整
        amount = amount.setScale(0, RoundingMode.DOWN);
        return amount;
    }


}