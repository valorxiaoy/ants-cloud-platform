package com.ants.mini.programs;

import com.ants.mini.programs.service.mq.order.IOrderRocketmqService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 小蚂蚁-支付系统-启动类
 *
 * @author Yueyang
 * @create 2020-11-09 19:15
 **/
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableBinding({IOrderRocketmqService.class})
@SpringBootApplication
public class AntsMiniProgramsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsMiniProgramsApplication.class);
    }
}