package com.ants.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ants.dubbo.api.service.activity.IAppletsActivityService;
import com.ants.dubbo.api.service.activity.IMatchingSmsBasicGiftsService;
import com.ants.dubbo.api.service.activity.IMatchingSmsCouponService;
import com.ants.dubbo.api.service.integral.IOrderIntegralService;
import com.ants.dubbo.api.service.order.IOrderServer;
import com.ants.module.activity.AppletsActivityDto;
import com.ants.module.activity.SmsBasicGiftsDto;
import com.ants.module.activity.SmsCouponHistoryDto;
import com.ants.module.constant.ActivityConstant;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.OmsOrderItemDto;
import com.ants.order.entity.OmsOrder;
import com.ants.order.entity.OmsOrderItem;
import com.ants.order.mapper.OmsOrderItemMapper;
import com.ants.order.mapper.OmsOrderMapper;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
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

    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @DubboReference
    private IMatchingSmsBasicGiftsService matchingSmsBasicGiftsService;

    @DubboReference
    private IMatchingSmsCouponService matchingSmsCouponService;

    @DubboReference
    private IOrderIntegralService orderIntegralService;

    @DubboReference
    private IAppletsActivityService appletsActivityService;

    @DubboReference
    private IGoodsBaseInfoService goodsBaseInfoService;

    @Override
    public OmsOrderDto searchOrder(String storeId, String memberId, String orderSn) {
        QueryWrapper<OmsOrder> omsOrderQueryWrapper = new QueryWrapper<>();
        omsOrderQueryWrapper.eq("store_id", storeId);
        omsOrderQueryWrapper.eq("member_id", memberId);
        omsOrderQueryWrapper.eq("order_sn", orderSn);

        List<OmsOrder> omsOrders = omsOrderMapper.selectList(omsOrderQueryWrapper);
        if (omsOrders == null || omsOrders.size() <= 0) {
            String exceptionMsg = String.format("未找到订单数据，参数orderSn：%s", orderSn);
            log.error(exceptionMsg);
            return null;
        } else {
            Optional<OmsOrder> first = omsOrders.stream().findFirst();
            OmsOrder omsOrder = first.get();

            OmsOrderDto omsOrderDto = new OmsOrderDto();
            BeanUtils.copyBeanProp(omsOrderDto, omsOrder);

            QueryWrapper<OmsOrderItem> omsOrderItemQueryWrapper = new QueryWrapper<>();
            omsOrderItemQueryWrapper.eq("order_sn", orderSn);
            List<OmsOrderItem> omsOrderItemList = omsOrderItemMapper.selectList(omsOrderItemQueryWrapper);
            List<OmsOrderItemDto> omsOrderItemDtoList = BeanUtils.converteToDtoArray(omsOrderItemList, OmsOrderItemDto.class);
            omsOrderDto.setOrderItemList(omsOrderItemDtoList);

            // 匹配赠品活动
            List<SmsBasicGiftsDto> smsBasicGiftsDtoList = matchingSmsBasicGiftsService.searchMatchingBasicGiftsByOrder(omsOrderDto);
            omsOrderDto.setSmsBasicGifts(smsBasicGiftsDtoList);

            // 匹配优惠卷
            List<SmsCouponHistoryDto> smsCouponHistoryDtoList = matchingSmsCouponService.searchMatchingCouponByOrder(omsOrderDto);
            omsOrderDto.setSmsCoupons(smsCouponHistoryDtoList);

            // 计算订单获得积分
            omsOrderDto = orderIntegralService.calculation(omsOrderDto);
            return omsOrderDto;
        }
    }

    @Override
    public OmsOrderDto searchOrderByOrderSn(String orderSn) {
        QueryWrapper<OmsOrder> omsOrderQueryWrapper = new QueryWrapper<>();
        omsOrderQueryWrapper.eq("order_sn", orderSn);

        List<OmsOrder> omsOrders = omsOrderMapper.selectList(omsOrderQueryWrapper);
        if (omsOrders == null || omsOrders.size() <= 0) {
            String exceptionMsg = String.format("未找到订单数据，参数orderSn：%s", orderSn);
            log.error(exceptionMsg);
            return null;
        } else {
            Optional<OmsOrder> first = omsOrders.stream().findFirst();
            OmsOrder omsOrder = first.get();

            OmsOrderDto omsOrderDto = new OmsOrderDto();
            BeanUtils.copyBeanProp(omsOrderDto, omsOrder);

            QueryWrapper<OmsOrderItem> omsOrderItemQueryWrapper = new QueryWrapper<>();
            omsOrderItemQueryWrapper.eq("order_sn", orderSn);
            List<OmsOrderItem> omsOrderItemList = omsOrderItemMapper.selectList(omsOrderItemQueryWrapper);
            List<OmsOrderItemDto> omsOrderItemDtoList = BeanUtils.converteToDtoArray(omsOrderItemList, OmsOrderItemDto.class);
            omsOrderDto.setOrderItemList(omsOrderItemDtoList);

            return omsOrderDto;
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

    /**
     * 重新计算订单数据
     * 该方法用于替换 Update方法
     *
     * @param omsOrderDto 订单数据(含优惠卷和参与活动)
     * @return 重新计算后的订单对象
     */
    @Override
    public OmsOrderDto recalculationOrder(OmsOrderDto omsOrderDto) {
        // 1. 计算优惠卷
        recalculationOrderByCoupon(omsOrderDto);
        // 2. 计算参与活动
        recalculationOrderByBasicGifts(omsOrderDto);
        // 3. 计算积分
        recalculationOrderByIntegral(omsOrderDto);
        // 4. 更新订单信息
        OmsOrder omsOrder = new OmsOrder();
        BeanUtils.copyBeanProp(omsOrder, omsOrderDto);
        boolean isUpdate = omsOrderMapper.updateById(omsOrder) > 0;
        if (!isUpdate) {
            throw new BusinessException("重新计算订单数据失败");
        }
        return omsOrderDto;
    }

    /**
     * 根据所选优惠卷 重新计算支付金额
     *
     * @param omsOrderDto 订单数据(含优惠卷和参与活动)
     * @return 重新计算后的订单对象
     */
    private OmsOrderDto recalculationOrderByCoupon(final OmsOrderDto omsOrderDto) {
        // 可用的优惠卷列表
        List<SmsCouponHistoryDto> smsCoupons = omsOrderDto.getSmsCoupons();
        // 选用的优惠卷ID
        Long couponId = omsOrderDto.getCouponId();
        if (couponId == null || smsCoupons == null || smsCoupons.size() == 0) {
            return omsOrderDto;
        }
        SmsCouponHistoryDto smsCouponHistoryDto = smsCoupons.stream().filter(bean -> couponId.equals(bean.getId())).findAny().orElse(null);
        BigDecimal couponAmount = smsCouponHistoryDto.getAmount();
        BigDecimal subtract = omsOrderDto.getPayAmount().subtract(couponAmount);
        if (subtract.compareTo(BigDecimal.ZERO) == -1) {
            subtract = BigDecimal.ZERO;
        }
        // 重设应付金额
        omsOrderDto.setPayAmount(subtract);
        // 设置优惠卷抵扣金额
        omsOrderDto.setCouponAmount(subtract);
        return omsOrderDto;
    }

    /**
     * 根据所选活动 重新计算订单数据
     *
     * @param omsOrderDto 订单数据(含优惠卷和参与活动)
     * @return 重新计算后的订单对象
     */
    private OmsOrderDto recalculationOrderByBasicGifts(final OmsOrderDto omsOrderDto) {
        // 找到对应的活动，并执行
        // 获取可参与活动列表
        List<SmsBasicGiftsDto> smsBasicGifts = omsOrderDto.getSmsBasicGifts();
        Integer appletsActivityId = omsOrderDto.getAppletsActivityId();
        AppletsActivityDto appletsActivityDto = appletsActivityService.searchAppletsActivityById(appletsActivityId);
        String activityType = appletsActivityDto.getActivityType();
        if (ActivityConstant.ACTIVITY_TYPE_BASICGIFTS.equals(activityType)) {
            String activityValue = appletsActivityDto.getActivityValue();
            SmsBasicGiftsDto smsBasicGiftsDtoBean = JSONObject.parseObject(activityValue, SmsBasicGiftsDto.class);
            SmsBasicGiftsDto smsBasicGiftsDto = smsBasicGifts.stream().filter(bean -> bean.getId().equals(smsBasicGiftsDtoBean.getId())).findAny().orElse(null);

            if (smsBasicGiftsDto != null) {
                // 获得赠品商品数据
                String goodValue = smsBasicGiftsDto.getGoodValue();
                // 创建赠品子订单
                addOmsOrderItem(goodValue, omsOrderDto);
            }
        }
        return omsOrderDto;
    }

    /**
     * 根据订单数据 重新计算订单积分
     *
     * @param omsOrderDto 订单数据(含优惠卷和参与活动)
     * @return 重新计算后的订单对象
     */
    private OmsOrderDto recalculationOrderByIntegral(final OmsOrderDto omsOrderDto) {
        return omsOrderDto;
    }

    /**
     * 根据赠品活动创建子订单
     *
     * @param goodValue   赠品商品IDs
     * @param omsOrderDto 订单对象
     */
    private void addOmsOrderItem(String goodValue, final OmsOrderDto omsOrderDto) {
        if (goodValue == null || "".equals(goodValue)) {
            log.error(String.format("赠品子订单创建失败，参数goodValue: %s", goodValue));
            return;
        }
        if (omsOrderDto == null || omsOrderDto.getStoreId() == null) {
            log.error(String.format("赠品子订单创建失败，参数omsOrderDto: %s", JSONObject.toJSONString(omsOrderDto)));
            return;
        }

        String[] split = goodValue.split(",");
        Integer storeId = omsOrderDto.getStoreId();
        Arrays.stream(split).forEach(goodsId -> {
            GoodsDetailedInformationDto bean = goodsBaseInfoService.searchGoodDetailedInfomationByGoodsId(String.valueOf(storeId), goodsId);
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            // 订单id
            omsOrderItem.setOrderId(omsOrderDto.getId());
            // 订单编号
            omsOrderItem.setOrderSn(omsOrderDto.getOrderSn());
            // 商品ID
            omsOrderItem.setProductId(bean.getId());
            // 商品图片
            omsOrderItem.setProductPic(bean.getGoodDetailed());
            // 商品名称
            omsOrderItem.setProductName(bean.getGoodName());
            // 商品副标题
            omsOrderItem.setProductSubheading(bean.getSubheading());
            // 商品品牌
            omsOrderItem.setProductBrand(String.valueOf(bean.getGoodBrandsId()));
            // 商品条码
            omsOrderItem.setProductSn(bean.getGoodTiaoCode());
            // 销售价格
            omsOrderItem.setProductPrice(bean.getRetailPrice());
            // 购买数量
            omsOrderItem.setProductQuantity(bean.getBuyNum());
            // 商品sku编号
            omsOrderItem.setProductSkuId(null);
            // 商品sku条码
            omsOrderItem.setProductSkuCode(null);
            // 商品分类id
            omsOrderItem.setProductCategoryId(bean.getProjectCategoryId());
            // 商品的销售属性
            omsOrderItem.setSp1(null);
            // 商品的销售属性
            omsOrderItem.setSp2(null);
            // 商品的销售属性
            omsOrderItem.setSp3(null);
            // 商品促销名称
            omsOrderItem.setPromotionName(null);
            // 商品促销分解金额
            omsOrderItem.setPromotionAmount(null);
            // 优惠券优惠分解金额
            omsOrderItem.setCouponAmount(null);
            // 积分优惠分解金额
            omsOrderItem.setIntegrationAmount(null);
            // 该商品经过优惠后的分解金额
            omsOrderItem.setRealAmount(null);
            omsOrderItem.setGiftIntegration(null);
            omsOrderItem.setGiftGrowth(null);
            // 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
            omsOrderItem.setProductAttr(null);
            // 所属店铺
            omsOrderItem.setStoreId(omsOrderDto.getStoreId());
            omsOrderItem.setStatus(null);
            omsOrderItem.setType(null);
            omsOrderItem.setStoreName(omsOrderDto.getStoreName());
            omsOrderItem.setIsFenxiao(null);
            omsOrderItem.setInviteMemberId(null);
            // 应付积分
            omsOrderItem.setProductIntegral(bean.getIntegral());

            boolean checkAdd = omsOrderItemMapper.insert(omsOrderItem) > 0;
            if (!checkAdd) {
                log.error(String.format("赠品子订单创建失败，参数omsOrderItem: %s", JSONObject.toJSONString(omsOrderItem)));
                return;
            }
        });
    }
}

