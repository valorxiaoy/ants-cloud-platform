package com.ants.pay.fy.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 富有支付 - 订单支付
 */
public interface IPayOrderService {

    /**
     * 富有支付 - 异步通知
     *
     * @param params   回调参数
     * @param response 请求响应
     */
    void payNotify(Map<String, String> params, HttpServletResponse response) throws Exception;
}
