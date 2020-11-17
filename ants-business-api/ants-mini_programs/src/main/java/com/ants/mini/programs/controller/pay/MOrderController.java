package com.ants.mini.programs.controller.pay;

import com.ants.mini.programs.dto.shopping.MShoppingCartDto;
import com.ants.mini.programs.service.pay.IMOrderService;
import com.ants.module.order.OmsOrderDto;
import com.ants.tools.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 下单
 */
@RestController
@Slf4j
public class MOrderController {

    @Autowired
    private IMOrderService omsOrderService;

    /**
     * 创建订单
     * @param storeId
     * @param memberId
     * @param sourceType
     * @param orderType
     * @param shoppingCartDtos
     * @return
     */
    @RequestMapping(value = "/mini/programs/order/{storeId}/{memberId}/{sourceType}/{orderType}", method = RequestMethod.POST)
    public Object createOrder(@PathVariable("storeId") String storeId, @PathVariable("memberId") String memberId,
                              @PathVariable("sourceType") String sourceType, @PathVariable("orderType") String orderType,
                              @RequestBody List<MShoppingCartDto> shoppingCartDtos) {
        try {
            OmsOrderDto omsOrder = omsOrderService.createOmsOrder(storeId, memberId, Integer.parseInt(sourceType), Integer.parseInt(orderType), shoppingCartDtos);
            if (omsOrder == null) {
                return new CommonResult().failed("订单创建失败");
            }
            return new CommonResult().success("查询成功", omsOrder);
        } catch (NumberFormatException e) {
            log.error("参数非法", e.getMessage(), e);
            return new CommonResult().failed("参数非法");
        } catch (Exception e) {
            log.error("未定义的接口异常", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
    }

    /**
     * 根据订单编号 查询待支付订单
     * @param storeId
     * @param memberId
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/mini/programs/order/{storeId}/{memberId}/{orderSn}", method = RequestMethod.POST)
    public Object searchOmsOrder(@PathVariable("storeId") String storeId, @PathVariable("memberId") String memberId, @PathVariable("orderSn") String orderSn) {
        try {
            OmsOrderDto omsOrderDto = omsOrderService.searchOmsOrder(storeId, memberId, orderSn);
            if (omsOrderDto == null) {
                return new CommonResult().failed("订单查询失败");
            }
            return new CommonResult().success("查询成功", omsOrderDto);
        } catch (NumberFormatException e) {
            log.error("参数非法", e.getMessage(), e);
            return new CommonResult().failed("参数非法");
        } catch (Exception e) {
            log.error("未定义的接口异常", e.getMessage(), e);
            return new CommonResult().failed("未定义的接口异常");
        }
    }

    @RequestMapping(value = "/mini/programs/order/{storeId}/{memberId}", method = RequestMethod.POST)
    public Object updateOrder(OmsOrderDto omsOrderDto) {
        try {
            OmsOrderDto updateDto = omsOrderService.update(omsOrderDto);
            if (updateDto == null) {
                return new CommonResult().failed("订单更新失败");
            }
            return new CommonResult().success("更新成功", updateDto);
        } catch (NumberFormatException e) {
            log.error("参数非法", e.getMessage(), e);
            return new CommonResult().failed("参数非法");
        } catch (Exception e) {
            log.error("未定义的接口异常", e.getMessage(), e);
            return new CommonResult().failed("未定义的接口异常");
        }
    }

}
