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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    public List<GoodsManagementDto> searchGoodsManagementByStoreId(String storeId) {
        try {
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper();
            queryWrapper.in("store_id", storeId, 0);
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
    public GoodsManagementDto searchGoodsManagementById(Integer id) {
        try {
            GoodsManagement goodsManagement = goodManagementMapper.selectById(id);
            if (goodsManagement == null) {
                String exceptionMsg = String.format("商品类别基础信息异常, 未找到商品分类, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            GoodsManagementDto goodsBrandDto = new GoodsManagementDto();
            BeanUtils.copyBeanProp(goodsBrandDto, goodsManagement);
            return goodsBrandDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodsManagementByStoreId(GoodsManagementDto goodsManagementDto) {
        try {
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("name", goodsManagementDto.getName());
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
    public boolean updateGoodsManagementByStoreId(GoodsManagementDto goodsManagementDto) {
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
    public boolean deleteGoodsManagementById(Integer id) {
        try {
            //前十五大类不允许删除
            QueryWrapper<GoodsManagement> queryWrapper = new QueryWrapper();
            queryWrapper.eq("pid", 0);
            List<GoodsManagement> list = goodManagementMapper.selectList(queryWrapper);
            Stream<GoodsManagement> limit = list.stream().limit(15);
            boolean present = limit.filter(m -> m.getId() == id).findAny().isPresent();
            if (present == true) {
                //TODO 前十五大类不允许删除 没返回
                return false;
            }
            GoodsManagement goodManagement = new GoodsManagement();
            goodManagement.setId(Integer.valueOf(id));
            goodManagement.setIsDelete(1);
            if (goodManagementMapper.updateById(goodManagement) < 0) {
                String exceptionMsg = String.format("商品类别基础信息异常, 类别删除失败, 参数goodManagement: %s", goodManagement);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }

    @Override
    public List<GoodsManagementDto> searchGoodsManagementBasicData(String storeId, Integer type) {
        List<GoodsManagementDto> brandDtoList = new ArrayList<>();
        List<GoodsManagementDto> bankDtoList = new ArrayList<>();
        QueryWrapper<GoodsManagement> queryCategory = new QueryWrapper();
        queryCategory.eq("pid", 0);
        List<GoodsManagement> categoryList = goodManagementMapper.selectList(queryCategory);
        //只要15类别
        if (storeId == null) {
            List<GoodsManagementDto> categoryDtos = BeanUtils.converteToDtoArray(categoryList, GoodsManagementDto.class);
            //不要赠品 和  服务项目
            if (type == 4) {
                categoryDtos.stream().limit(13);
            }
            return categoryDtos;
        }
        if (type == 2) {
            //只要品牌
            categoryList.forEach((bean) -> {
                QueryWrapper<GoodsManagement> queryBrand = new QueryWrapper();
                queryBrand.eq("store_id", storeId);
                queryBrand.eq("is_delete", 0);
                queryBrand.eq("pid", bean.getId());
                List<GoodsManagement> brandList = goodManagementMapper.selectList(queryBrand);
                List<GoodsManagementDto> brandDtos = BeanUtils.converteToDtoArray(brandList, GoodsManagementDto.class);
                brandDtos.forEach((brand) -> {
                    GoodsManagementDto dto = new GoodsManagementDto();
                    BeanUtils.copyProperties(bean, dto);
                    brandDtoList.add(dto);
                });
            });
            return brandDtoList;
        }
        if (type == 3) {
            //只要系列
            brandDtoList.forEach((bean) -> {
                QueryWrapper<GoodsManagement> queryBrand = new QueryWrapper();
                queryBrand.eq("store_id", storeId);
                queryBrand.eq("is_delete", 0);
                queryBrand.eq("pid", bean.getId());
                List<GoodsManagement> brandList = goodManagementMapper.selectList(queryBrand);
                List<GoodsManagementDto> brandDtos = BeanUtils.converteToDtoArray(brandList, GoodsManagementDto.class);
                brandDtos.forEach((brand) -> {
                    GoodsManagementDto dto = new GoodsManagementDto();
                    BeanUtils.copyProperties(bean, dto);
                    bankDtoList.add(dto);
                });
            });
            return bankDtoList;
        }
        return null;
    }
}
