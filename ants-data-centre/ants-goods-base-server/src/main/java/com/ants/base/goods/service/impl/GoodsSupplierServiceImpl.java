package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsSupplier;
import com.ants.base.goods.mapper.GoodsSupplierMapper;
import com.ants.dubbo.api.base.goods.IGoodsSupplierService;
import com.ants.module.goods.base.dto.GoodsSupplierDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小米
 * @date 2020-11-19
 */
@Slf4j
@DubboService
public class GoodsSupplierServiceImpl extends ServiceImpl
        <GoodsSupplierMapper, GoodsSupplier> implements IGoodsSupplierService {

    @Resource
    private GoodsSupplierMapper goodSupplierMapper;


    @Override
    public List<GoodsSupplierDto> searchGoodsSupplierByStoreId(Integer storeId) {
        try {
            QueryWrapper<GoodsSupplier> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<GoodsSupplier> list = goodSupplierMapper.selectList(queryWrapper);
            List<GoodsSupplierDto> goodSupplierDtos = BeanUtils.converteToDtoArray(list, GoodsSupplierDto.class);
            return goodSupplierDtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsSupplierDto searchGoodsSupplierById(Integer id) {
        try {
            QueryWrapper<GoodsSupplier> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            GoodsSupplier goodSupplier = goodSupplierMapper.selectOne(queryWrapper);
            if (goodSupplier == null) {
                String exceptionMsg = String.format("供应商异常, 未找到该供应商, 参数goodSupplier: %s", goodSupplier);
                throw new BusinessException(exceptionMsg);
            }
            GoodsSupplierDto goodSupplierDto = new GoodsSupplierDto();
            BeanUtils.copyProperties(goodSupplier, goodSupplierDto);
            return goodSupplierDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodsSupplierByStoreId(GoodsSupplierDto goodSupplierDto) {
        try {
            //校验
            QueryWrapper<GoodsSupplier> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", goodSupplierDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("phone_code", goodSupplierDto.getPhoneCode()).or().eq("supplier", goodSupplierDto.getSupplier()));
            queryWrapper.notIn("id", goodSupplierDto.getId());
            List<GoodsSupplier> list = goodSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商名字或者手机号已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            //新增
            GoodsSupplier goodSupplier = new GoodsSupplier();
            BeanUtils.copyProperties(goodSupplierDto, goodSupplier);
            if (goodSupplierMapper.insert(goodSupplier) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商添加失败, 参数goodSupplier: %s", goodSupplier);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodsSupplierByStoreId(GoodsSupplierDto goodSupplierDto) {
        try {
            //校验
            QueryWrapper<GoodsSupplier> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", goodSupplierDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.and(i -> i.eq("phone_code", goodSupplierDto.getPhoneCode()).or().eq("supplier", goodSupplierDto.getSupplier()));
            queryWrapper.notIn("id", goodSupplierDto.getId());
            List<GoodsSupplier> list = goodSupplierMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商名字或者手机号已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            //修改
            GoodsSupplier goodSupplier = new GoodsSupplier();
            BeanUtils.copyProperties(goodSupplierDto, goodSupplier);
            if (goodSupplierMapper.updateById(goodSupplier) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商修改失败, 参数goodSupplier: %s", goodSupplier);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGoodsSupplierById(Integer id) {
        try {
            GoodsSupplier goodSupplier = new GoodsSupplier();
            goodSupplier.setId(id);
            goodSupplier.setIsDelete(1);
            if (goodSupplierMapper.updateById(goodSupplier) < 0) {
                String exceptionMsg = String.format("商品供应商异常, 供应商删除失败, 参数goodSupplier: %s", goodSupplier);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
