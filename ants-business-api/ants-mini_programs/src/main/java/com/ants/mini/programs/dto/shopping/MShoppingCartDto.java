package com.ants.mini.programs.dto.shopping;

import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MShoppingCartDto {
    /**
     * 会员id
     * */
    private Integer memberId;
    /**
     * 门店id
     * */
    private Integer storeId;
    /**
     * 商品id
     * */
    private Integer goodsId;
    /**
     * 商品名称
     * */
    private String goodsName;
    /**
     * 商品价格
     * */
    private BigDecimal goodsPrice;
    /**
     * 商品积分
     * */
    private Integer integral = 0;
    /**
     * 商品图片
     * */
    private String goodsPic;
    /**
     * 商品副标题
     * */
    private String goodsSubtitle;
    /**
     * 购买数量 默认值为 1
     * */
    private Integer num;
    /**
     * 订单来源：0->PC订单；1->app订单
     */
    private String sourceType;
    /**
     * 订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
     */
    private String orderType;
    /**
     * 购买商品
     */
    private GoodsDetailedInformationDto goodDetailedInformationDto;
}
