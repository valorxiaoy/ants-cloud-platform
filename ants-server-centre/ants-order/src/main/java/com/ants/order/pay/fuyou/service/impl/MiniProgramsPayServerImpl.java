package com.ants.order.pay.fuyou.service.impl;

import com.ants.dubbo.api.service.order.IMiniProgramsPayServer;
import com.ants.order.pay.fuyou.constant.FyPayConstant;
import com.ants.order.pay.fuyou.util.FyPayUtil;
import com.ants.tools.exception.ApiException;
import com.ants.tools.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 富有支付-小程序支付
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
@Slf4j
@DubboService
public class MiniProgramsPayServerImpl implements IMiniProgramsPayServer {

    @Override
    public Map<String, String> buildOrderMap(Map<String, String> orderMap) throws Exception {
        Map<String, String> nvs = new HashMap<>(FyPayConstant.HASH_MAP_DEFAULT);

        Map<String, String> reqMap = new HashMap<>(orderMap);

        String sign = FyPayUtil.getSign(reqMap);
        reqMap.put("sign", sign);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");

        for (String key : reqMap.keySet()) {
            String value = reqMap.get(key);
            root.addElement(key).addText(value);
        }

        String reqBody = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>" + doc.getRootElement().asXML();

        log.info(String.format("==============================待编码字符串==============================\n%s", reqBody));

        reqBody = URLEncoder.encode(reqBody, "GBK");

        log.info(String.format("==============================编码后字符串==============================\n%s", reqBody));

        nvs.put("req", reqBody);

        StringBuffer result = new StringBuffer();
        HttpUtils.post(FyPayConstant.ORDER_CREATE_URL, nvs, result);
        String rspXml = URLDecoder.decode(result.toString(), "GBK");

        log.info(String.format("==============================响应报文==============================\n%s", rspXml));
        //响应报文验签
        if (rspXml != null && !"".equals(rspXml)) {
            Map<String, String> resMap = FyPayUtil.xmlStr2Map(rspXml);
            String str = resMap.get("sign");

            Boolean verifySign = FyPayUtil.verifySign(resMap, str);

            log.info(String.format("==============================验签结果==============================\n%s", verifySign));
            if (verifySign) {
                return resMap;
            }
        } else {
            // TODO 需要重试机制
            throw new ApiException("下单接口无响应");
        }

        return null;
    }
}