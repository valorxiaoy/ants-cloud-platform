package com.ants.module.shopping;

import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import lombok.Data;

import java.util.List;

/**
 * 购物车对象
 *
 * @author Yueyang
 * @create 2020-11-16 21:22
 **/
@Data
public class ShoppingCartDto {
    /**
     * 门店ID
     */
    private Integer storeId;
    /**
     * 会员ID
     */
    private Integer memberId;
    /**
     * 订单来源：0->PC订单；1->app订单
     */
    private Integer sourceType;
    /**
     * 订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
     */
    private Integer orderType;
    /**
     * 购买商品详情
     */
    private List<GoodsDetailedInformationDto> goodsDetailedInformationDtoList;
}