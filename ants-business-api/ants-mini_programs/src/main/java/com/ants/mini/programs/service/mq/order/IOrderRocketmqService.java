package com.ants.mini.programs.service.mq.order;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 订单服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IOrderRocketmqService {

    @Output("pre-create-order-output")
    MessageChannel preCreateOrder();
}
