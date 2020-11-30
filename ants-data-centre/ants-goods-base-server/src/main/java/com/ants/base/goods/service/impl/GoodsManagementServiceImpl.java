package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsManagement;
import com.ants.base.goods.mapper.GoodsManagementMapper;
import com.ants.dubbo.api.base.goods.IGoodsManagementService;
import com.ants.module.goods.base.dto.GoodsManagementDto;
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
 * @date 2020-11-20
 */
@Slf4j
@DubboService
public class GoodsManagementServiceImpl extends ServiceImpl
        <GoodsManagementMapper, GoodsManagement> implements IGoodsManagementService {

    @Resource
    private GoodsManagementMapper goodManagementMapper;


    @Override
    public List<GoodsManagementDto> searchGoodManagement(GoodsManagementDto goodsManagementDto) {
        try {
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper();
            if (goodsManagementDto.getPid() == 0) {
                queryWrapper.in("store_id", goodsManagementDto.getStoreId(), 0);
            } else {
                queryWrapper.eq("store_id", goodsManagementDto.getStoreId());
                queryWrapper.eq("pid", goodsManagementDto.getId());
            }
            queryWrapper.eq("is_delete", 0);
            List<GoodsManagement> list = goodManagementMapper.selectList(queryWrapper);
            List<GoodsManagementDto> goodsManagementDtos = BeanUtils.converteToDtoArray(list, GoodsManagementDto.class);
            return goodsManagementDtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsManagementDto searchGoodManagementById(Integer id) {
        try {
            GoodsManagement goodBrand = goodManagementMapper.selectById(id);
            if (goodBrand == null) {
                String exceptionMsg = String.format("商品类别基础信息异常, 未找到商品分类, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            GoodsManagementDto goodsBrandDto = new GoodsManagementDto();
            BeanUtils.copyBeanProp(goodsBrandDto, goodBrand);
            return goodsBrandDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodManagement(GoodsManagementDto goodsManagementDto) {
        try {
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", goodsManagementDto.getName());
            queryWrapper.notIn("id", goodsManagementDto.getId());
            List<GoodsManagement> list = goodManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 商品分类中已存在该名称, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsManagement goodManagement = new GoodsManagement();
            BeanUtils.copyProperties(goodsManagementDto, goodManagement);
            if (goodManagementMapper.insert(goodManagement) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别添加失败, 参数goodManagement: %s", goodManagement);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodManagement(GoodsManagementDto goodsManagementDto) {
        try {
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", goodsManagementDto.getName());
            queryWrapper.notIn("id", goodsManagementDto.getId());
            List<GoodsManagement> list = goodManagementMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 商品分类中已存在该名称, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsManagement goodManagement = new GoodsManagement();
            BeanUtils.copyProperties(goodsManagementDto, goodManagement);
            if (goodManagementMapper.updateById(goodManagement) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别修改失败, 参数goodManagement: %s", goodManagement);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGoodManagement(GoodsManagementDto goodsManagementDto) {
        try {
            GoodsManagement goodManagement = new GoodsManagement();
            BeanUtils.copyProperties(goodsManagementDto, goodManagement);
            goodManagement.setIsDelete(1);
            if (goodManagementMapper.updateById(goodManagement) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别删除失败, 参数goodManagement: %s", goodManagement);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (
                BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
