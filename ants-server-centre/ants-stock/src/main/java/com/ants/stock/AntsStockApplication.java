package com.ants.stock;

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
@MapperScan({"com.ants.stock.mapper"})
@SpringBootApplication
public class AntsStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsStockApplication.class);
    }
}