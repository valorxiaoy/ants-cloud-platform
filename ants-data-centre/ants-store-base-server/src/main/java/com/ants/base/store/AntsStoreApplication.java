package com.ants.base.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小蚂蚁-支付系统-启动类
 *
 * @author Yueyang
 * @create 2020-11-09 19:15
 **/
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan({"com.ants.base.store.mapper"})
@SpringBootApplication
public class AntsStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsStoreApplication.class);
    }
}