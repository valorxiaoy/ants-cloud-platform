package com.ants.order.pay.fuyou;

import com.ants.order.pay.fuyou.service.IOrderChannelServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小蚂蚁-支付系统-启动类
 *
 * @author Yueyang
 * @create 2020-11-09 19:15
 **/
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan({"com.ants.order.pay.fuyou.mapper"})
@EnableBinding({ Source.class, IOrderChannelServer.class })
@SpringBootApplication
public class AntsOrderPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsOrderPayApplication.class);
    }
}