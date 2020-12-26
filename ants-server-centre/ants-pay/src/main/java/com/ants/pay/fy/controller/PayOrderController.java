package com.ants.pay.fy.controller;

import com.ants.pay.fy.service.IPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 支付
 */
@RestController
@Slf4j
public class PayOrderController {

    @Autowired
    private IPayOrderService payOrderService;

    @PostMapping("/fuiou/pay/notify")
    public void payNotify(@RequestParam Map<String, String> params, HttpServletResponse response) {
        try {
            // 验证回调，并处理订单状态
            payOrderService.payNotify(params, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
