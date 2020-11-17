package com.ants.base.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小蚂蚁-商品基础信息系统-启动类
 *
 * @author Yueyang
 * @create 2020-11-09 19:15
 **/
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan({"com.ants.base.goods.mapper"})
@SpringBootApplication
public class AntsGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsGoodsApplication.class);
    }
}