package com.ants.mini.programs.service.pay;

import com.ants.mini.programs.dto.shopping.MShoppingCartDto;
import com.ants.module.order.OmsOrderDto;

import java.util.List;

public interface IOrderService {

    OmsOrderDto createOmsOrder(String storeId, String memberId, Integer sourceType, Integer orderType, List<MShoppingCartDto> shoppingCartDtos);

    OmsOrderDto searchOmsOrder(String storeId, String memberId, String orderSn);

    OmsOrderDto update(OmsOrderDto omsOrderDto);
}
