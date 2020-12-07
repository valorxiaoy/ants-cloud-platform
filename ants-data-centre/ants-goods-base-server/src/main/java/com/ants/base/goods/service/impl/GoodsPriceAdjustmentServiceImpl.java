package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsPriceAdjustment;
import com.ants.base.goods.entity.GoodsPriceAdjustmentItem;
import com.ants.base.goods.mapper.GoodsPriceAdjustmentItemMapper;
import com.ants.base.goods.mapper.GoodsPriceAdjustmentMapper;
import com.ants.dubbo.api.base.goods.IGoodsPriceAdjustmentService;
import com.ants.module.goods.base.dto.GoodsPriceAdjustmentDto;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 小米
 * @date 2020-11-20
 */
@Slf4j
@DubboService
public class GoodsPriceAdjustmentServiceImpl extends ServiceImpl
        <GoodsPriceAdjustmentMapper, GoodsPriceAdjustment> implements IGoodsPriceAdjustmentService {

    @Resource
    private GoodsPriceAdjustmentMapper goodPriceAdjustmentMapper;
    @Autowired
    private GoodsPriceAdjustmentItemMapper goodPriceAdjustmentItemMapper;

    @Override
    public List<GoodsPriceAdjustmentDto> searchGoodsPriceAdjustment(GoodsPriceAdjustmentDto goodPriceAdjustmentDto) {
        try {
            QueryWrapper<GoodsPriceAdjustment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", goodPriceAdjustmentDto.getStoreId());
            queryWrapper.eq("delete_status", 0);
            queryWrapper.eq("state", goodPriceAdjustmentDto.getState());
            queryWrapper.between("maker_time", goodPriceAdjustmentDto.getStartTime(), goodPriceAdjustmentDto.getEndTime());
            List<GoodsPriceAdjustment> list = goodPriceAdjustmentMapper.selectList(queryWrapper);
            list.forEach((bean) -> {
                //制单人类型是商户还是员工
                if (bean.getUserType() == 1) {
                    //sys_user 表
                    bean.getUserId();
                } else if (bean.getUserType() == 2 || bean.getUserType() == 3) {
                    //员工表
                    bean.getUserId();
                }
                //审核人类型是商户还是员工
                if (bean.getAuditorUserType() == 1) {
                    //SYS_USER 表
                    bean.getAuditorUserId();
                } else if (bean.getAuditorUserType() == 2 || bean.getAuditorUserType() == 3) {
                    //员工表
                }
            });
            List<GoodsPriceAdjustmentDto> goodPriceAdjustmentDtos = BeanUtils.converteToDtoArray(list, GoodsPriceAdjustmentDto.class);
            return goodPriceAdjustmentDtos;
        } catch (
                BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsPriceAdjustmentDto searchGoodsPriceAdjustmentById(Integer id) {
        try {
            GoodsPriceAdjustment goodPriceAdjustment = goodPriceAdjustmentMapper.selectById(id);
            if (goodPriceAdjustment == null) {
                String exceptionMsg = String.format("商品调价单异常, 未找到商品调价单, 参数goodPriceAdjustment: %s", goodPriceAdjustment);
                throw new BusinessException(exceptionMsg);
            }
            GoodsPriceAdjustmentDto goodPriceAdjustmentDto = new GoodsPriceAdjustmentDto();
            BeanUtils.copyProperties(goodPriceAdjustment, goodPriceAdjustmentDto);
            return goodPriceAdjustmentDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createGoodsPriceAdjustmentByStoreId(List<GoodsDetailedInformationDto> list, Integer storeId) {
        try {
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //生成的入库单号
            String order_sn = "RK" + time.format(new Date());
            //存大表
            GoodsPriceAdjustment goodPriceAdjustment = new GoodsPriceAdjustment();
            goodPriceAdjustment.setState(0);
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            //制单日期
            goodPriceAdjustment.setMakerTime(new Date());
            //单号
            goodPriceAdjustment.setOrderId(order_sn);
            //TODO 当前登陆人类型制单人类型
//            goodPriceAdjustment.setUserType();
            //添加调价单
            if (goodPriceAdjustmentMapper.insert(goodPriceAdjustment) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单创建失败, 参数goodPriceAdjustment: %s", goodPriceAdjustment);
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            GoodsPriceAdjustmentItem goodPriceAdjustmentItem = new GoodsPriceAdjustmentItem();
            list.forEach((bean) -> {
                //商品id
                goodPriceAdjustmentItem.setGoodId(bean.getId());
                //批发价
                goodPriceAdjustmentItem.setWholesalePrice(bean.getWholesalePrice());
                //零售价
                goodPriceAdjustmentItem.setRetailPrice(bean.getRetailPrice());
                //单号
                goodPriceAdjustmentItem.setOrderId(order_sn);
                //添加调价单明细
                if (goodPriceAdjustmentItemMapper.insert(goodPriceAdjustmentItem) < 0) {
                    String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数goodPriceAdjustmentItem: %s", goodPriceAdjustmentItem);
                    throw new BusinessException(exceptionMsg);
                }
            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoodsPriceAdjustmentByStoreId(List<GoodsDetailedInformationDto> list, String orderSn, Integer storeId) {
        try {
            //存大表
            GoodsPriceAdjustment goodPriceAdjustment = new GoodsPriceAdjustment();
            //TODO 当前登录人id
//            goodPriceAdjustment.setUserId();
            //TODO 当前登陆人类型制单人类型
//            goodPriceAdjustment.setUserType();
            QueryWrapper<GoodsPriceAdjustment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", orderSn);
            //修改调价单
            if (goodPriceAdjustmentMapper.update(goodPriceAdjustment, queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单修改失败, 参数goodPriceAdjustment: %s", goodPriceAdjustment);
                throw new BusinessException(exceptionMsg);
            }
            //删除明细
            QueryWrapper<GoodsPriceAdjustmentItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", orderSn);
            if (goodPriceAdjustmentItemMapper.delete(itemWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单修改失败删除明细, 参数order_id: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            //存明细表
            GoodsPriceAdjustmentItem goodPriceAdjustmentItem = new GoodsPriceAdjustmentItem();
            list.forEach((bean) -> {
                //商品id
                goodPriceAdjustmentItem.setGoodId(bean.getId());
                //批发价
                goodPriceAdjustmentItem.setWholesalePrice(bean.getWholesalePrice());
                //零售价
                goodPriceAdjustmentItem.setRetailPrice(bean.getRetailPrice());
                //单号
                goodPriceAdjustmentItem.setOrderId(orderSn);
                //添加调价单明细
                if (goodPriceAdjustmentItemMapper.insert(goodPriceAdjustmentItem) < 0) {
                    String exceptionMsg = String.format("商品调价单异常, 商品调价单明细添加失败, 参数goodPriceAdjustmentItem: %s", goodPriceAdjustmentItem);
                    throw new BusinessException(exceptionMsg);
                }
            });
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGoodsPriceAdjustmentByOrderSn(String orderSn) {
        try {
            //删价格改价表
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_id", orderSn);
            if (goodPriceAdjustmentMapper.delete(queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单删除失败, 参数order_id: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            if (goodPriceAdjustmentItemMapper.delete(queryWrapper) < 0) {
                String exceptionMsg = String.format("商品调价单异常, 商品调价单明细删除失败, 参数order_id: %s", orderSn);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
