package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.GoodsGrade;
import com.ants.base.goods.mapper.GoodsGradeMapper;
import com.ants.dubbo.api.base.goods.IGoodsGradeService;
import com.ants.module.goods.base.dto.GoodsGradeDto;
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
public class GoodsGradeServiceImpl extends ServiceImpl
        <GoodsGradeMapper, GoodsGrade> implements IGoodsGradeService {

    @Resource
    private GoodsGradeMapper goodGradeMapper;


    @Override
    public List<GoodsGradeDto> searchGoodGrade(Integer storeId) {
        try {
            QueryWrapper<GoodsGrade> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("store_id", storeId);
            List<GoodsGrade> list = goodGradeMapper.selectList(queryWrapper);
            List<GoodsGradeDto> goodGradeDtos = BeanUtils.converteToDtoArray(list, GoodsGradeDto.class);
            return goodGradeDtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsGradeDto searchGoodGradeById(Integer id) {
        try {
            QueryWrapper<GoodsGrade> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", id);
            GoodsGrade goodGrade = goodGradeMapper.selectOne(queryWrapper);
            if (goodGrade == null) {
                String exceptionMsg = String.format("商品等级异常, 未找到商品等级, 参数goodGradey: %s", goodGrade);
                throw new BusinessException(exceptionMsg);
            }
            GoodsGradeDto goodGradeDto = new GoodsGradeDto();
            BeanUtils.copyProperties(goodGrade, goodGradeDto);
            return goodGradeDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodGrade(GoodsGradeDto goodGradeDto) {
        try {
            QueryWrapper<GoodsGrade> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", goodGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_grade", goodGradeDto.getGoodGrade());
            queryWrapper.notIn("id", goodGradeDto.getId());
            List<GoodsGrade> list = goodGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品等级异常, 等级名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsGrade goodGrade = new GoodsGrade();
            BeanUtils.copyProperties(goodGradeDto, goodGrade);
            if (goodGradeMapper.insert(goodGrade) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级添加失败, 参数goodGrade: %s", goodGrade);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodGrade(GoodsGradeDto goodGradeDto) {
        try {
            QueryWrapper<GoodsGrade> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", goodGradeDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_grade", goodGradeDto.getGoodGrade());
            queryWrapper.notIn("id", goodGradeDto.getId());
            List<GoodsGrade> list = goodGradeMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品等级异常, 等级名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsGrade goodGrade = new GoodsGrade();
            BeanUtils.copyProperties(goodGradeDto, goodGrade);
            if (goodGradeMapper.updateById(goodGrade) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级添加失败, 参数goodGrade: %s", goodGrade);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGoodGrade(GoodsGradeDto goodGradeDto) {
        try {
            GoodsGrade goodGrade = new GoodsGrade();
            BeanUtils.copyProperties(goodGradeDto, goodGrade);
            goodGrade.setIsDelete(1);
            if (goodGradeMapper.updateById(goodGrade) < 0) {
                String exceptionMsg = String.format("商品等级异常, 等级删除失败, 参数goodGrade: %s", goodGrade);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
