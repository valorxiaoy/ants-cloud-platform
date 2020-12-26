package com.ants.order.service.impl;

import com.ants.dubbo.api.service.activity.IAppletsActivityService;
import com.ants.dubbo.api.service.member.IMemberSerivce;
import com.ants.order.constant.OrderConstant;
import com.ants.order.entity.OmsOrder;
import com.ants.order.mapper.OmsOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 支付成功-处理订单状态服务
 *
 * @author Yueyang
 * @create 2020-12-02 19:05
 **/
@Slf4j
@Component
public class OrderPaySuessusServerImpl {

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @DubboReference
    private IAppletsActivityService appletsActivityService;

    @DubboReference
    private IMemberSerivce memberSerivce;

    @StreamListener("pay-suessus-order-input")
    public void paySuessusOrder(String orderSn) {
        if (orderSn == null || "".equals(orderSn)) {
            return;
        }

        QueryWrapper<OmsOrder> omsOrderQueryWrapper = new QueryWrapper<>();
        omsOrderQueryWrapper.eq("order_sn", orderSn);
        OmsOrder omsOrder = omsOrderMapper.selectOne(omsOrderQueryWrapper);

        // 订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
        Integer orderType = omsOrder.getOrderType();
        // 订单状态：0->待付款； 8->拼团进行中 ;1->付款中；2->待发货；3->待签收；4->已完成；5->退货 6->换货 7->已关闭
        // 如果是拼团，转成待拼团状态
        if (orderType.equals(OrderConstant.ORDER_TYPE_MAKE_GROUP)) {
            omsOrder.setStatus(OrderConstant.ORDER_STATUS_MAKE_GROUP);
        } else {
            omsOrder.setStatus(OrderConstant.ORDER_STATUS_TO_BE_DELIVERED);
        }
        // 更新订单信息
        omsOrderMapper.updateById(omsOrder);

        // TODO 处理流水信息

        // TODO 以下代码需要改成MQ形式

        // 更新活动数据
        Integer appletsActivityId = omsOrder.getAppletsActivityId();
        appletsActivityService.partakeInActivityById(appletsActivityId);

        // 本单获得积分
        Long memberId = omsOrder.getMemberId();
        Double integration = omsOrder.getIntegration();
        if (integration > 0) {
            memberSerivce.memberAddIntegral(memberId.intValue(), integration.intValue());
        }

        // 扣除个人积分
        Integer useIntegration = omsOrder.getUseIntegration();
        if (useIntegration > 0) {
            memberSerivce.memberReduceIntegral(memberId.intValue(), useIntegration);
        }


    }
}