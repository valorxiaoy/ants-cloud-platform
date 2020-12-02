package com.ants.pay.fy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ants.dubbo.api.service.order.IMiniProgramsPayServer;
import com.ants.module.order.OmsOrderDto;
import com.ants.pay.fy.config.FyPayParams;
import com.ants.pay.fy.constant.FyPayConstant;
import com.ants.pay.fy.entity.SmsWxPay;
import com.ants.pay.fy.mapper.SmsWxPayMapper;
import com.ants.pay.fy.util.FyPayUtil;
import com.ants.pay.fy.util.FyRandomNumberGenerator;
import com.ants.tools.exception.ApiException;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.HttpUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Autowired
    private SmsWxPayMapper smsWxPayMapper;

    @Override
    public Map<String, String> payOrder(String openId, OmsOrderDto omsOrderDto) {
        Map<String, String> preOrderMap = getPreOrderMap(openId, omsOrderDto);
        try {
            Map<String, String> buildOrderMap = buildOrderMap(preOrderMap);
            return buildOrderMap;
        } catch (Exception e) {
            log.error("支付失败,富有响应报文解析失败.");
            log.error(e.getMessage());
            return null;
        }
    }

    private Map<String, String> getPreOrderMap(String openId, OmsOrderDto omsOrderDto) {
        log.info(String.format("OpenId: %s, 订单详情：%s", openId, JSONObject.toJSONString(omsOrderDto)));

        // 门店ID
        Integer storeId = omsOrderDto.getStoreId();
        // 获取富有支付信息
        SmsWxPay fyPayInfo = getFyPayInfo(storeId);

        Map<String, String> map = new HashMap<>();
        // 必填 接口版本号
        map.put("version", FyPayParams.FY_VERSION);
        // 必填 机构x`号,接入机构在富友的唯一代码
        map.put("ins_cd", FyPayParams.FY_INS_CD);
        // 必填 商户号,富友分配给二级商户的商户号
        map.put("mchnt_cd", fyPayInfo.getMchId());
        // 必填 终端号(没有真实终端号统一填88888888)
        map.put("term_id", FyPayParams.FY_TERM_ID);
        // 必填 随机字符串
        map.put("random_str", FyRandomNumberGenerator.generateNumber());
        // 必填 签名, 详见签名生成算法
        map.put("sign", "");
        // 商品描述, 商品或支付单简要描述
        map.put("goods_des", "");
        // 单品优惠功能字段，见文档中 http://fundwx.fuiou.com/doc/#/scanpay/introduction?id=goods_detail%e8%af%b4%e6%98%8e%e5%ad%97%e6%ae%b5
        map.put("goods_detail", "");
        // 商品标记
        map.put("goods_tag", "");
        // 商品标识
        map.put("product_id", "");
        // 附加数据
        map.put("addn_inf", "");
        // 必填 商户订单号,商户系统内部的订单号（5到30个字符、只能包含字母数字,区分大小写)
        map.put("mchnt_order_no", omsOrderDto.getOrderSn());
        // 货币类型,默认人民币：CNY
        map.put("curr_type", FyPayParams.FY_CURR_TYPE);
        // 必填 总金额,订单总金额,单位为分
        if (omsOrderDto.getTotalAmount() == null) {
            return null;
        }
        map.put("order_amt", FyPayParams.getAmount(omsOrderDto.getTotalAmount()).toString());
        // 必填 终端IP
        map.put("term_ip", FyPayParams.FY_TERM_IP);
        // 必填 交易起始时间,订单生成时间,格式为yyyyMMddHHmmss
        SimpleDateFormat sdf_no = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        map.put("txn_begin_ts", sdf_no.format(calendar.getTime()));
        // 必填 通知地址,接收富友异步通知回调地址,通知url必须为直接可访问的url,不能携带参数
        map.put("notify_url", FyPayParams.ORDER_NOTIFY_URL);
        /*
         * 限制支付,
         * no_credit:不能使用信用卡
         * credit_group：不能使用花呗以及信用卡
         */
        map.put("limit_pay", "");
        /*
         * 必填 订单类型:
         * JSAPI--公众号支付
         * FWC--支付宝服务窗、支付宝小程序
         * LETPAY-微信小程序
         * BESTPAY--翼支付js
         */
        map.put("trade_type", FyPayParams.FY_ORDER_TYPE_LETPAY);
        // 用户标识(暂已废弃,不影响已对接完成的)
        map.put("openid", "");
        // 子商户用户标识
        // 支付宝服务窗为用户buyer_id(此场景必填)
        // 微信公众号为用户的openid(小程序,公众号,服务窗必填)
        map.put("sub_openid", openId);
        // 子商户公众号id, 微信交易为商户的appid(小程序,公众号必填)
        map.put("sub_appid", fyPayInfo.getAppId());
        return map;
    }

    /**
     * 预编译订单参数(富有支付)
     *
     * @param orderMap 参数
     * @return 编译后的参数列表
     * @throws Exception 签名失败
     */
    private Map<String, String> buildOrderMap(Map<String, String> orderMap) throws Exception {
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

    /**
     * 获取富有支付信息
     *
     * @param storeId 门店ID
     * @return 富有支付信息
     */
    private SmsWxPay getFyPayInfo(Integer storeId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id", storeId);
        SmsWxPay smsWxPay = smsWxPayMapper.selectOne(queryWrapper);
        if (smsWxPay == null || "".equals(smsWxPay.getMchId()) || "".equals(smsWxPay.getAppId())) {
            throw new BusinessException(String.format("当前门店未配置富有商户号或小程序APP_ID, 参数storeId: %s", storeId));
        }
        return smsWxPay;
    }
}