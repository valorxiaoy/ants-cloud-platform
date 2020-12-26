package com.ants.pay.fy.service.impl;

import com.ants.dubbo.api.service.order.IOrderServer;
import com.ants.pay.fy.service.IPayOrderService;
import com.ants.pay.fy.util.FyPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 富有支付 - 订单支付服务
 *
 * @author Yueyang
 * @create 2020-12-02 17:43
 **/
@Slf4j
@Service
public class PayOrderServiceImpl implements IPayOrderService {

    @DubboReference
    private IOrderServer orderServer;

    @Override
    public void payNotify(Map<String, String> params, HttpServletResponse response) throws Exception {
        if (params != null && params.containsKey("req")) {
            String reqXml = params.get("req");
            //解码
            reqXml = URLDecoder.decode(reqXml, "GBK");
            //响应报文验签
            if (reqXml != null && !"".equals(reqXml)) {
                Map<String, String> resMap = FyPayUtil.xmlStr2Map(reqXml);
                if (resMap.containsKey("sign")) {
                    String str = resMap.get("sign");
                    Boolean verifySign = FyPayUtil.verifySign(resMap, str);
                    if (verifySign && resMap.containsKey("mchnt_order_no")) {

                        // 回调成功，回写结果
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(1);
                        out.flush();
                        out.close();

                        String mchnt_order_no = resMap.get("mchnt_order_no");
                        // TODO 更新订单数据
                        // TODO 保存回调通知结果
                    }
                }
            }
            // throw new ApiMallPlusException("下单接口回调报文异常");
        }
    }
}