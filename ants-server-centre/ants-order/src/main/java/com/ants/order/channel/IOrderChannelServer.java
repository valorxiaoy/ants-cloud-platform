package com.ants.order.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 订单服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IOrderChannelServer {

    /**
     * 消费预创建订单
     *
     * @return 返回通道
     */
    @Input("pre-create-order-input")
    SubscribableChannel preCreateOrder();
}
