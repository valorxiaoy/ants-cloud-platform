package com.ants.dubbo.api.service.order;

import java.util.Map;

/**
 * 富有支付-小程序支付
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IMiniProgramsPayServer {
    /**
     * 预编译订单参数(富有支付)
     *
     * @param orderMap 参数
     * @return 编译后的参数列表
     * @throws Exception 签名失败
     */
    Map<String, String> buildOrderMap(Map<String, String> orderMap) throws Exception;
}
