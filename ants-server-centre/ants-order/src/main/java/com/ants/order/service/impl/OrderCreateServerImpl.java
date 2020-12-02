/**
 * 关于RocketMQ的另外一种实现
 *
 * @Slf4j
 * @Component
 * @DubboService
 * @RocketMQMessageListener(topic = "Order-Created", consumerGroup = "order-created-consumer-group")
 * public class OrderServerImpl implements RocketMQListener<String> {
 * @Override public void onMessage(String message) {
 * // 检查订单数据是否合法
 * ShoppingCartDto shoppingCartDto = checkShoppingCart(message);
 * // 获取门店数据
 * SysStoreDto sysStoreDto = searchStoreById(shoppingCartDto);
 * // 获取会员数据
 * UmsMemberDto umsMemberDto = searchMemberById(shoppingCartDto);
 * // 预创建订单&子订单
 * OmsOrderDto omsOrderDto = preCreateOrder(sysStoreDto, umsMemberDto, shoppingCartDto);
 * // 保存订单数据
 * saveOrder(omsOrderDto);
 * // TODO 处理订单流水
 * }
 * }
 */
package com.ants.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ants.dubbo.api.base.goods.IGoodsManagementService;
import com.ants.dubbo.api.base.member.IMemberBaseService;
import com.ants.dubbo.api.base.store.IStoreService;
import com.ants.dubbo.api.service.integral.IOrderIntegralService;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.module.goods.base.dto.GoodsManagementDto;
import com.ants.module.member.UmsMemberDto;
import com.ants.module.order.OmsOrderDto;
import com.ants.module.order.OmsOrderItemDto;
import com.ants.module.shopping.ShoppingCartDto;
import com.ants.module.store.SysStoreDto;
import com.ants.order.entity.OmsOrder;
import com.ants.order.entity.OmsOrderItem;
import com.ants.order.mapper.OmsOrderItemMapper;
import com.ants.order.mapper.OmsOrderMapper;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.ants.tools.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建预支付订单
 * <p>
 * 流程简书
 * 1.0 创建订单
 * 1.1 检查参数
 * 1.2 获取门店数据
 * 1.3 获取会员数据
 * 1.4 创建主订单
 * 1.5 创建子订单
 * 1.6 计算积分
 * 1.7 生成核销码
 * 1.8 保存主订单数据
 * 1.9 保存子订单数据
 * 2.0 删除购物车
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
@Slf4j
@Component
public class OrderCreateServerImpl {

    /**
     * 数据中心
     */
    private final static long dataCenterId = 1;

    /**
     * 机器标识
     */
    private final static long machineId = 1;

    /**
     * 基于分布式的订单获取方法
     * 订单生成工具
     */
    private final static SnowFlakeUtils SNOW_FLAKE = new SnowFlakeUtils(dataCenterId, machineId);

    /**
     * 富有订单前缀
     */
    private final static String FUYOU_PAY_ORDER_PREFIX = "1327";

    /**
     * 订单核销码位数
     */
    private final static int VERIFICATION_CODE_COUNT = 6;

    @DubboReference
    private IStoreService storeService;

    @DubboReference
    private IMemberBaseService memberService;

    @DubboReference
    private IOrderIntegralService orderIntegralService;

    @DubboReference
    private IGoodsManagementService goodManagementService;

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @StreamListener("pre-create-order-input")
    public void preCreateOrder(String message) {
        // 检查订单数据是否合法
        ShoppingCartDto shoppingCartDto = checkShoppingCart(message);
        // 获取门店数据
        SysStoreDto sysStoreDto = searchStoreById(shoppingCartDto);
        // 获取会员数据
        UmsMemberDto umsMemberDto = searchMemberById(shoppingCartDto);
        // 预创建订单&子订单
        OmsOrderDto omsOrderDto = preCreateOrder(sysStoreDto, umsMemberDto, shoppingCartDto);
        // 保存订单数据
        saveOrder(omsOrderDto);
        // TODO 处理订单流水
    }

    @ServiceActivator(inputChannel = "pre-create-order-topic.pre-create-order-group.errors")
    public void handleConsumeUserError(ErrorMessage message) {
        log.info("收到处理失败的消息{}", message.getPayload());
    }

    @StreamListener("errorChannel")
    public void handleErrors(ErrorMessage message) {
        log.info("默认的消息失败处理器收到处理失败的消息: {}，headers：{}", message.getOriginalMessage(), message.getHeaders());
    }

    /**
     * 检查购物车数据
     *
     * @param value MQ-购物车数据
     * @return 购物车对象
     */
    private ShoppingCartDto checkShoppingCart(String value) {
        // 检查主订单数据
        if ("".equals(value)) {
            throw new BusinessException(String.format("非法的购物车对象, 购物车数据: %s", value));
        }

        ShoppingCartDto shoppingCartDto = JSONObject.parseObject(value, ShoppingCartDto.class);

        if (shoppingCartDto == null) {
            throw new BusinessException(String.format("购物车对象转换失败, 购物车数据: %s", value));
        }

        // TODO 检查子订单数据

        return shoppingCartDto;
    }

    /**
     * 根据门店ID查询门店信息
     *
     * @param shoppingCartDto 购物车数据
     * @return 门店对象
     */
    private SysStoreDto searchStoreById(ShoppingCartDto shoppingCartDto) {
        Integer storeId = shoppingCartDto.getStoreId();
        if (storeId == null || storeId == 0) {
            throw new BusinessException(String.format("门店信息不存在, 查询ID为: %s", storeId));
        }
        SysStoreDto sysStoreDto = storeService.searchSysStore(storeId);
        if (sysStoreDto == null) {
            throw new BusinessException(String.format("未找到ID未%s的门店信息", storeId));
        }
        return sysStoreDto;
    }

    /**
     * 根据门店ID查询总店数据
     *
     * @param shoppingCartDto 购物车数据
     * @return 总店对象
     */
    private SysStoreDto searchMasterStoreByBranchStoreId(ShoppingCartDto shoppingCartDto) {
        Integer storeId = shoppingCartDto.getStoreId();
        if (storeId == null || storeId == 0) {
            throw new BusinessException(String.format("门店信息不存在, 查询ID为: %s", storeId));
        }
        SysStoreDto sysStoreDto = storeService.searchMasterStoreByBranchStoreId(storeId);
        if (sysStoreDto == null) {
            throw new BusinessException(String.format("未找到ID未%s的门店信息", storeId));
        }
        return sysStoreDto;
    }

    /**
     * 根据ID查询会员数据
     *
     * @param shoppingCartDto 购物车数据
     * @return 会员对象
     */
    private UmsMemberDto searchMemberById(ShoppingCartDto shoppingCartDto) {
        Integer memberId = shoppingCartDto.getMemberId();
        if (memberId == null || memberId == 0) {
            throw new BusinessException(String.format("会员信息不存在, 查询ID为: %s", memberId));
        }

        UmsMemberDto umsMemberDto = memberService.searchUmsMember(memberId);
        if (umsMemberDto == null) {
            throw new BusinessException(String.format("未找到ID为%s的会员信息", memberId));
        }
        return umsMemberDto;
    }

    /**
     * 创建预支付订单
     *
     * @param sysStoreDto     门店数据
     * @param umsMemberDto    会员数据
     * @param shoppingCartDto 购物车数据
     * @return 预支付订单
     */
    private OmsOrderDto preCreateOrder(SysStoreDto sysStoreDto, UmsMemberDto umsMemberDto, ShoppingCartDto shoppingCartDto) {
        // 设置订单基础数据
        OmsOrderDto omsOrderDto = setOrderBaseInfo(sysStoreDto, umsMemberDto, shoppingCartDto);
        // 设置子订单基础数据
        setOmsOrderItem(omsOrderDto, shoppingCartDto);
        // 计算总金额&应付金额
        setTotalAmount(omsOrderDto);
        // 计算使用积分(总积分)&应付积分
        calculation(omsOrderDto);
        // 生成核销码
        createVerificationCode(omsOrderDto);
        return omsOrderDto;
    }

    /**
     * 设置订单基础信息
     * @param sysStoreDto 门店数据
     * @param umsMemberDto 会员数据
     * @param shoppingCartDto 购物车数据
     * @return 订单数据
     */
    private OmsOrderDto setOrderBaseInfo(SysStoreDto sysStoreDto, UmsMemberDto umsMemberDto, ShoppingCartDto shoppingCartDto) {
        if (sysStoreDto == null || umsMemberDto == null || shoppingCartDto == null) {
            String exceptionMsg = String.format("设置订单基础信息失败%n参数SysStoreDto: %s%n参数umsMemberDto: %s%n参数ShoppingCartDto: %s%n", JSONObject.toJSONString(sysStoreDto), JSONObject.toJSONString(umsMemberDto), JSONObject.toJSONString(shoppingCartDto));
            throw new BusinessException(exceptionMsg);
        }

        // 获取系统当前日期
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowTime = dtf2.format(time);

        // 订单编号
        String orderSn = createOrderSn();
        OmsOrderDto omsOrderDto = new OmsOrderDto();
        // 设置会员编号
        omsOrderDto.setMemberId(umsMemberDto.getId());
        // 设置优惠券编号
        omsOrderDto.setCouponId(null);
        // 设置订单编号， 富有订单前缀+订单号
        omsOrderDto.setOrderSn(orderSn);
        // 设置开始时间
        omsOrderDto.setCreateTime(nowTime);
        // 设置会员账户
        omsOrderDto.setMemberUsername(umsMemberDto.getPhone());
        // 设置订单总金额
        omsOrderDto.setTotalAmount(new BigDecimal(0));
        // 设置实际支付金额
        omsOrderDto.setPayAmount(new BigDecimal(0));
        // 设置运费金额
        omsOrderDto.setFreightAmount(new BigDecimal(0));
        // 设置促销优化金额（促销价、满减、阶梯价）
        omsOrderDto.setPromotionAmount(new BigDecimal(0));
        // 设置积分抵扣金额
        omsOrderDto.setIntegrationAmount(new BigDecimal(0));
        // 设置优惠券抵扣金额
        omsOrderDto.setCouponAmount(new BigDecimal(0));
        // 设置管理员后台调整订单使用的折扣金额
        omsOrderDto.setDiscountAmount(new BigDecimal(0));
        // 设置支付方式：0->未支付；1->支付宝；2->微信；3->积分支付
        omsOrderDto.setPayType(0);
        // 设置订单来源：0->PC订单；1->app订单
        omsOrderDto.setSourceType(shoppingCartDto.getSourceType());
        // 设置支付状态（隐藏状态）
        omsOrderDto.setPaymentStatus(0);
        // 设置订单状态：0->待付款； 8->拼团进行中 ;1->付款中；2->待发货；3->待签收；4->已完成；5->退货 6->换货 7->已关闭
        omsOrderDto.setStatus(0);
        // 设置订单类型：0->正常订单；1->秒杀订单 ;2->门店自取订单 ；3->拼团订单；4->团购订单；5->积分订单
        omsOrderDto.setOrderType(shoppingCartDto.getOrderType());
        // 设置物流公司(配送方式)
        omsOrderDto.setDeliveryCompany(null);
        // 设置物流单号
        omsOrderDto.setDeliverySn(null);
        // 设置自动确认时间（天）
        omsOrderDto.setAutoConfirmDay(0);
        // 设置可以获得的积分
        omsOrderDto.setIntegration(0.00);
        // 设置可以活动的成长值
        omsOrderDto.setGrowth(0);
        // 设置活动信息
        omsOrderDto.setPromotionInfo(null);
        // 设置发票类型：0->不开发票；1->电子发票；2->纸质发票
        omsOrderDto.setBillType(0);
        // 设置发票抬头
        omsOrderDto.setBillHeader(null);
        // 设置发票内容
        omsOrderDto.setBillContent(null);
        // 设置收票人电话
        omsOrderDto.setBillReceiverPhone(null);
        // 设置收票人邮箱
        omsOrderDto.setBillReceiverEmail(null);
        // 设置收货人姓名
        omsOrderDto.setReceiverName(null);
        // 设置收货人电话
        omsOrderDto.setReceiverPhone(null);
        // 设置收货人邮编
        omsOrderDto.setReceiverPostCode(null);
        // 设置省份/直辖市
        omsOrderDto.setReceiverProvince(null);
        // 设置城市
        omsOrderDto.setReceiverCity(null);
        // 设置区
        omsOrderDto.setReceiverRegion(null);
        // 设置详细地址
        omsOrderDto.setReceiverDetailAddress(null);
        // 设置订单备注
        omsOrderDto.setNote(null);
        // 设置确认收货状态：0->未确认；1->已确认
        omsOrderDto.setConfirmStatus(0);
        // 设置删除状态：0->未删除；1->已删除
        omsOrderDto.setDeleteStatus(0);
        // 设置下单时使用的积分
        omsOrderDto.setUseIntegration(0);
        // 设置支付时间
        omsOrderDto.setPaymentTime(null);
        // 设置发货时间
        omsOrderDto.setDeliveryTime(null);
        // 设置确认收货时间
        omsOrderDto.setReceiveTime(null);
        // 设置评价时间
        omsOrderDto.setCommentTime(null);
        // 设置修改时间
        omsOrderDto.setModifyTime(null);
        // 设置预付编号
        omsOrderDto.setPrepayId(null);
        // 设置供货商编号
        omsOrderDto.setSupplyId(null);
        // 设置商品编号
        omsOrderDto.setGoodsId(null);
        // 设置商品名称
        omsOrderDto.setGoodsName(null);
        // 设置学校编号
        omsOrderDto.setSchoolId(null);
        // 设置店铺信息
        omsOrderDto.setStoreId(sysStoreDto.getId());
        // 设置负责人编号
        omsOrderDto.setReceiverId(null);
        // 设置组编号
        omsOrderDto.setGroupId(null);
        // 设置是否开发票 0=不发票 1=个人发票 2=公司发票
        omsOrderDto.setTaxType(0);
        // 设置发票内容
        omsOrderDto.setTaxContent(null);
        // 设置税号
        omsOrderDto.setTaxCode(null);
        // 设置发票抬头
        omsOrderDto.setTaxTitle(null);
        // 设置是否评论，1未评论，2已评论
        omsOrderDto.setIsComment(1);
        // 设置店铺名称
        omsOrderDto.setStoreName(sysStoreDto.getName());
        // 设置总店id
        SysStoreDto masterStore = searchMasterStoreByBranchStoreId(shoppingCartDto);
        if (masterStore != null) {
            omsOrderDto.setPid(masterStore.getId());
        }
        // 设置Vip价格
        omsOrderDto.setVipAmount(null);
        // 设置消费门店
        omsOrderDto.setStoreConsumeId(null);
        // 设置应付积分
        omsOrderDto.setPayIntegral(0);
        // 设置是否删除
        omsOrderDto.setIsDelete(0);

        return omsOrderDto;
    }

    /**
     * 设置子订单基础数据
     * @param omsOrderDto 订单数据
     * @param shoppingCartDto 购物车数据
     */
    private void setOmsOrderItem(final OmsOrderDto omsOrderDto, ShoppingCartDto shoppingCartDto) {
        List<GoodsDetailedInformationDto> goodsDetailedInformationDtoList = shoppingCartDto.getGoodsDetailedInformationDtoList();
        if (goodsDetailedInformationDtoList == null || goodsDetailedInformationDtoList.size() <= 0) {
            String exceptionMsg = String.format("设置子订单基础信息失败%n参数OmsOrderDto: %s%n参数ShoppingCartDto: %s%n", JSONObject.toJSONString(omsOrderDto), JSONObject.toJSONString(shoppingCartDto));
            throw new BusinessException(exceptionMsg);
        }

        List<OmsOrderItemDto> omsOrderItemDtoList = new ArrayList<>();

        goodsDetailedInformationDtoList.forEach(bean -> {
            // 获取商品品牌
            Integer goodsBrandsId = bean.getGoodBrandsId();
            GoodsManagementDto goodsBrandDto = goodManagementService.searchGoodManagementById(goodsBrandsId);

            OmsOrderItemDto omsOrderItemDto = new OmsOrderItemDto();
            // 订单id
            omsOrderItemDto.setOrderId(omsOrderDto.getId());
            // 订单编号
            omsOrderItemDto.setOrderSn(omsOrderDto.getOrderSn());
            // 商品ID
            omsOrderItemDto.setProductId(bean.getId());
            // 商品图片
            omsOrderItemDto.setProductPic(bean.getGoodDetailed());
            // 商品名称
            omsOrderItemDto.setProductName(bean.getGoodName());
            // 商品副标题
            omsOrderItemDto.setProductSubheading(bean.getSubheading());
            // 商品品牌
            if (goodsBrandDto != null) {
                omsOrderItemDto.setProductBrand(goodsBrandDto.getName());
            }
            // 商品条码
            omsOrderItemDto.setProductSn(bean.getGoodTiaoCode());
            // 销售价格
            omsOrderItemDto.setProductPrice(bean.getRetailPrice());
            // 购买数量
            omsOrderItemDto.setProductQuantity(bean.getBuyNum());
            // 商品sku编号
            omsOrderItemDto.setProductSkuId(null);
            // 商品sku条码
            omsOrderItemDto.setProductSkuCode(null);
            // 商品分类id
            omsOrderItemDto.setProductCategoryId(bean.getProjectCategoryId());
            // 商品的销售属性
            omsOrderItemDto.setSp1(null);
            // 商品的销售属性
            omsOrderItemDto.setSp2(null);
            // 商品的销售属性
            omsOrderItemDto.setSp3(null);
            // 商品促销名称
            omsOrderItemDto.setPromotionName(null);
            // 商品促销分解金额
            omsOrderItemDto.setPromotionAmount(null);
            // 优惠券优惠分解金额
            omsOrderItemDto.setCouponAmount(null);
            // 积分优惠分解金额
            omsOrderItemDto.setIntegrationAmount(null);
            // 该商品经过优惠后的分解金额
            omsOrderItemDto.setRealAmount(null);
            omsOrderItemDto.setGiftIntegration(null);
            omsOrderItemDto.setGiftGrowth(null);
            // 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
            omsOrderItemDto.setProductAttr(null);
            // 所属店铺
            omsOrderItemDto.setStoreId(omsOrderDto.getStoreId());
            omsOrderItemDto.setStatus(null);
            omsOrderItemDto.setType(null);
            omsOrderItemDto.setStoreName(omsOrderDto.getStoreName());
            omsOrderItemDto.setIsFenxiao(null);
            omsOrderItemDto.setInviteMemberId(null);
            // 应付积分
            omsOrderItemDto.setProductIntegral(bean.getIntegral());

            omsOrderItemDtoList.add(omsOrderItemDto);
        });

        if (omsOrderItemDtoList.size() <= 0) {
            String exceptionMsg = String.format("设置子订单基础信息失败%n参数OmsOrder: %s%n参数ShoppingCartDto: %s%n", JSONObject.toJSONString(omsOrderDto), JSONObject.toJSONString(shoppingCartDto));
            throw new BusinessException(exceptionMsg);
        }
        omsOrderDto.setOrderItemList(omsOrderItemDtoList);
    }

    /**
     * 设置订单总金额&应付金额
     *
     * @param omsOrderDto 订单数据
     */
    private void setTotalAmount(final OmsOrderDto omsOrderDto) {
        List<OmsOrderItemDto> orderItemList = omsOrderDto.getOrderItemList();
        orderItemList.forEach(bean -> {
            BigDecimal productPrice = bean.getProductPrice();
            Integer productQuantity = bean.getProductQuantity();
            BigDecimal amount = productPrice.multiply(new BigDecimal(productQuantity));

            BigDecimal totalAmount = omsOrderDto.getTotalAmount() != null ? omsOrderDto.getTotalAmount() : BigDecimal.ZERO;
            totalAmount = totalAmount.add(amount);

            omsOrderDto.setTotalAmount(totalAmount);
            omsOrderDto.setPayAmount(totalAmount);
        });
    }

    /**
     * 计算订单获得积分
     *
     * @param omsOrderDto 订单数据
     */
    private void calculation(final OmsOrderDto omsOrderDto) {
        OmsOrderDto calculation = orderIntegralService.calculation(omsOrderDto);
        if (calculation == null) {
            String exceptionMsg = String.format("计算订单积分失败, 参数%s", JSONObject.toJSONString(omsOrderDto));
            throw new BusinessException(exceptionMsg);
        }
    }

    /**
     * 创建订单单号
     *
     * @return 订单单号
     */
    private String createOrderSn() {
        return FUYOU_PAY_ORDER_PREFIX + SNOW_FLAKE.nextId();
    }

    /**
     * 创建订单核销码
     *
     * @param omsOrderDto 订单数据
     */
    private void createVerificationCode(final OmsOrderDto omsOrderDto) {
        StringBuilder writeOffCode = new StringBuilder();
        for (int i = 0; i < VERIFICATION_CODE_COUNT; i++) {
            writeOffCode.append((int) Math.floor(Math.random() * 10));
        }
        int hashCode = String.format("%s%s", omsOrderDto.getOrderSn(), writeOffCode.toString()).hashCode();
        omsOrderDto.setVerificationCode(Integer.toString(hashCode));
    }

    /**
     * 保存订单信息
     * @param omsOrderDto 订单数据
     */
    private void saveOrder(OmsOrderDto omsOrderDto) {
        OmsOrder omsOrder = new OmsOrder();
        BeanUtils.copyBeanProp(omsOrder, omsOrderDto);

        boolean checkOrderUpdate = omsOrderMapper.insert(omsOrder) > 0;

        if (!checkOrderUpdate) {
            String exceptionMsg = String.format("订单保存失败, 参数OmsOrderDto: %s", JSONObject.toJSONString(omsOrderDto));
            throw new BusinessException(exceptionMsg);
        }

        List<OmsOrderItemDto> orderItemList = omsOrderDto.getOrderItemList();
        if (orderItemList == null || orderItemList.size() <= 0) {
            String exceptionMsg = String.format("订单保存失败, 参数List<OmsOrderItemDto>: %s", JSONArray.toJSONString(orderItemList));
            throw new BusinessException(exceptionMsg);
        }

        orderItemList.forEach(bean -> {
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            BeanUtils.copyBeanProp(omsOrderItem, bean);
            boolean checkOrderItemUpdate = omsOrderItemMapper.insert(omsOrderItem) > 0;
            if (!checkOrderItemUpdate) {
                String exceptionMsg = String.format("订单保存失败, 参数OmsOrderItemDto: %s", JSONObject.toJSONString(bean));
                throw new BusinessException(exceptionMsg);
            }
        });
    }
}