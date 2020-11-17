package com.ants.mini.programs.service.pay.impl;

import com.alibaba.fastjson.JSONArray;
import com.ants.dubbo.api.service.order.IOrderServer;
import com.ants.mini.programs.dto.shopping.MShoppingCartDto;
import com.ants.mini.programs.service.pay.IMOrderService;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.shopping.ShoppingCartDto;
import com.ants.tools.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MOrderServiceImpl implements IMOrderService {

    @DubboReference
    private IOrderServer orderServer;

    @Override
    public OmsOrderDto createOmsOrder(String storeId, String memberId, Integer sourceType, Integer orderType, List<MShoppingCartDto> shoppingCartDtos) {
        // 组装真正的购物车对象
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setStoreId(Integer.valueOf(storeId));
        shoppingCartDto.setMemberId(Integer.valueOf(memberId));
        shoppingCartDto.setSourceType(sourceType);
        shoppingCartDto.setOrderType(orderType);
        List<GoodsDetailedInformationDto> goodsDetailedInformationDtoList = new ArrayList<>();
        shoppingCartDtos.forEach(bean -> {
            GoodsDetailedInformationDto goodDetailedInformationDto = bean.getGoodDetailedInformationDto();
            goodsDetailedInformationDtoList.add(goodDetailedInformationDto);
        });
        shoppingCartDto.setGoodsDetailedInformationDtoList(goodsDetailedInformationDtoList);

        String notifyAddress = "";
        omsOrderProducer(JSONArray.toJSONString(shoppingCartDto), notifyAddress);
        return null;
    }

    @Override
    public OmsOrderDto searchOmsOrder(String storeId, String memberId, String orderSn) {
        OmsOrderDto omsOrderDto = orderServer.searchOrder(orderSn);
        return omsOrderDto;
    }

    /**
     * 选择优惠卷和活动后，更新订单数据
     * @param omsOrderDto
     * @return
     */
    @Override
    public OmsOrderDto update(OmsOrderDto omsOrderDto) {
        OmsOrderDto updateDto = orderServer.update(omsOrderDto);
        return updateDto;
    }

    /**
     * 发送创建订单任务
     * @param msg 下单JSON对象
     * @throws BusinessException 业务异常
     */
    private void omsOrderProducer(String msg, String notifyAddress) {
        try {
            // TODO 将配置抽取到配置文件中
            DefaultMQProducer producer = new DefaultMQProducer("producer_group");
            producer.setNamesrvAddr("192.168.80.208:9876");
            producer.setRetryTimesWhenSendAsyncFailed(1);
            producer.start();

            Message message = new Message("pre-create-order-topic", "tagStr", msg.getBytes());

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.debug("sendResult:{}", sendResult);
                    webserviceNotify(notifyAddress, "ok");
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("订单创建失败:", throwable);
                    webserviceNotify(notifyAddress, "bad");
                }
            });
        } catch (MQClientException | InterruptedException | RemotingException e) {
            webserviceNotify(notifyAddress, "bad");
            log.error("订单创建失败:" + e.getMessage());
        }
    }

    /**
     * 通知下单结果
     */
    public void webserviceNotify(String url, String result) {
        // TODO 发送通知到具体的客户端
    }
}