package com.ants.activity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小蚂蚁-活动系统-启动类
 *
 * @author Yueyang
 * @create 2020-11-09 19:15
 **/
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan({"com.ants.activity.mapper"})
@SpringBootApplication
public class AntsActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsActivityApplication.class);
    }
}