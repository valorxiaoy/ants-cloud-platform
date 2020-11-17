package com.ants.order.pay.fuyou.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020-11-16 6:46
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "TopicTest", consumerGroup = "my-consumer-group")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("我收到消息了！消息内容为：" + message);
    }
}