package com.ants.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@MapperScan({"com.ants.member.mapper"})
@SpringBootApplication
public class AntsMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsMemberApplication.class);
    }
}