package com.ants.order.pay.fuyou.service.impl;

import com.ants.dubbo.api.service.order.IOrderServer;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.SmsCouponHistoryDto;
import com.ants.order.pay.fuyou.entity.OmsOrder;
import com.ants.order.pay.fuyou.mapper.OmsOrderMapper;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 订单服务
 *
 * @author Yueyang
 * @create 2020-11-17 8:18
 **/
@Slf4j
@DubboService
public class OrderServerImpl implements IOrderServer {

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @Override
    public OmsOrderDto searchOrder(String orderSn) {
        try {
            QueryWrapper<OmsOrder> omsOrderQueryWrapper = new QueryWrapper<>();
            omsOrderQueryWrapper.eq("order_sn", orderSn);

            List<OmsOrder> omsOrders = omsOrderMapper.selectList(omsOrderQueryWrapper);
            if (omsOrders == null || omsOrders.size() <= 0) {
                String exceptionMsg = String.format("未找到订单数据，参数orderSn：%s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            Optional<OmsOrder> first = omsOrders.stream().findFirst();
            OmsOrder omsOrder = first.get();

            OmsOrderDto omsOrderDto = new OmsOrderDto();
            BeanUtils.copyProperties(omsOrderDto, omsOrder);

            return omsOrderDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public OmsOrderDto update(OmsOrderDto omsOrderDto) {
        if (omsOrderDto != null && omsOrderDto.getId() != null) {
            List<SmsCouponHistoryDto> smsCoupons = omsOrderDto.getSmsCoupons();
            // 计算应付金额
            if (smsCoupons != null && smsCoupons.size() > 0) {
                smsCoupons.forEach(bean -> {
                    BigDecimal smsCouponsAmount = smsCoupons.stream().map(SmsCouponHistoryDto::getAmount).reduce(BigDecimal::add).get();
                    BigDecimal totalAmount = omsOrderDto.getTotalAmount();
                    totalAmount = totalAmount.subtract(smsCouponsAmount);
                    omsOrderDto.setCouponAmount(smsCouponsAmount);
                    omsOrderDto.setPayAmount(totalAmount);
                });
            }

            OmsOrder omsOrder = new OmsOrder();
            BeanUtils.copyBeanProp(omsOrder, omsOrderDto);

            boolean isUpdate = omsOrderMapper.updateById(omsOrder) > 0;
            if (isUpdate) {
                return omsOrderDto;
            }
        }
        return null;
    }
}

