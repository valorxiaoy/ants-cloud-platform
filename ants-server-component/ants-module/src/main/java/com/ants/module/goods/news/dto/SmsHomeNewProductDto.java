package com.ants.module.goods.news.dto;

import com.ants.module.BaseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SmsHomeNewProductDto extends BaseDto {

    /**
     * 商品详情
     */
    private String goodDetailedInformation;

    /**
     * 商品副标题
     */
    private String subheading;

    private Long id;

    private Long productId;

    private String productName;

    private Integer recommendStatus;

    private Integer sort;

    private BigDecimal price;

    private String pic;

}
