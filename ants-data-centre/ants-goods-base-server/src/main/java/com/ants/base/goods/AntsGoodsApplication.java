package com.ants.base.goods;

import com.alibaba.fastjson.JSONObject;
import com.ants.base.goods.entity.GoodBrand;
import com.ants.base.goods.mapper.GoodBrandMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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