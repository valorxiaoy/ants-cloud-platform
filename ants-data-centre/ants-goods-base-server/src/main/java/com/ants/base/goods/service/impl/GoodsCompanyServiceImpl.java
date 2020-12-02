package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.GoodsCompany;
import com.ants.base.goods.mapper.GoodsCompanyMapper;
import com.ants.dubbo.api.base.goods.IGoodsCompanyService;
import com.ants.module.goods.base.dto.GoodsCompanyDto;
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
public class GoodsCompanyServiceImpl extends ServiceImpl
        <GoodsCompanyMapper, GoodsCompany> implements IGoodsCompanyService {

    @Resource
    private GoodsCompanyMapper goodCompanyMapper;


    @Override
    public List<GoodsCompanyDto> searchGoodCompany(Integer storeId) {
        try {
            QueryWrapper<GoodsCompany> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id", storeId);
            queryWrapper.eq("is_delete", 0);
            List<GoodsCompany> list = goodCompanyMapper.selectList(queryWrapper);
            List<GoodsCompanyDto> dtos = BeanUtils.converteToDtoArray(list, GoodsCompanyDto.class);
            return dtos;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsCompanyDto searchGoodCompanyById(Integer id) {
        try {
            QueryWrapper<GoodsCompany> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            GoodsCompany goodCompany = goodCompanyMapper.selectOne(queryWrapper);
            if (goodCompany == null) {
                String exceptionMsg = String.format("商品单位异常, 未找到该单位, 参数goodCompany: %s", goodCompany);
                throw new BusinessException(exceptionMsg);
            }
            GoodsCompanyDto goodCompanyDto = new GoodsCompanyDto();
            BeanUtils.copyProperties(goodCompany, goodCompanyDto);
            return goodCompanyDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodCompany(GoodsCompanyDto goodCompanyDto) {
        try {
            QueryWrapper<GoodsCompany> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", goodCompanyDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_company", goodCompanyDto.getGoodCompany());
            queryWrapper.notIn("id", goodCompanyDto.getId());
            List<GoodsCompany> list = goodCompanyMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品单位异常, 单位名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsCompany goodCompany = new GoodsCompany();
            BeanUtils.copyProperties(goodCompanyDto, goodCompany);
            if (goodCompanyMapper.insert(goodCompany) < 0) {
                String exceptionMsg = String.format("商单位异常, 单位添加失败, 参数goodCompany: %s", goodCompany);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodCompany(GoodsCompanyDto goodCompanyDto) {
        try {
            QueryWrapper<GoodsCompany> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id", goodCompanyDto.getStoreId());
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_company", goodCompanyDto.getGoodCompany());
            queryWrapper.notIn("id", goodCompanyDto.getId());
            List<GoodsCompany> list = goodCompanyMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                String exceptionMsg = String.format("商品单位异常, 单位名字已存在, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            GoodsCompany goodCompany = new GoodsCompany();
            BeanUtils.copyProperties(goodCompanyDto, goodCompany);
            if (goodCompanyMapper.updateById(goodCompany) < 0) {
                String exceptionMsg = String.format("商单位异常, 单位修改失败, 参数goodCompany: %s", goodCompany);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGoodCompany(GoodsCompanyDto goodCompanyDto) {
        try {
            GoodsCompany goodCompany = new GoodsCompany();
            BeanUtils.copyProperties(goodCompanyDto, goodCompany);
            goodCompany.setIsDelete(1);
            if (goodCompanyMapper.updateById(goodCompany) < 0) {
                String exceptionMsg = String.format("商单位异常, 单位删除失败, 参数goodCompany: %s", goodCompany);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
