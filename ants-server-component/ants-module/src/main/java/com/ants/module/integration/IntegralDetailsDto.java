package com.ants.module.integration;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: ants-cloud-platform
 * @description: 积分规则详情
 * @author: 刘智
 * @create: 2020-11-30 16:50
 **/
@Data
public class IntegralDetailsDto implements Serializable {
    /**
     * 类别id
     */
    private Integer id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 积分
     */
    private Double integral;
}
