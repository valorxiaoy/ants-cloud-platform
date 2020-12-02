package com.ants.base.goods.service.impl;

import com.ants.base.goods.entity.GoodsCompany;
import com.ants.base.goods.entity.GoodsDetailedInformation;
import com.ants.base.goods.entity.GoodsManagement;
import com.ants.base.goods.entity.GoodsSupplier;
import com.ants.base.goods.mapper.GoodsCompanyMapper;
import com.ants.base.goods.mapper.GoodsDetailedInformationMapper;
import com.ants.base.goods.mapper.GoodsManagementMapper;
import com.ants.base.goods.mapper.GoodsSupplierMapper;
import com.ants.dubbo.api.base.goods.IGoodsDetailedInformationService;
import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
import com.ants.tools.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import com.ants.tools.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 小米
 * @date 2020-11-19
 */
@Slf4j
@DubboService
public class GoodsDetailedInformationServiceImpl implements IGoodsDetailedInformationService {

    @Autowired
    private GoodsDetailedInformationMapper goodDetailedInformationMapper;
    @Autowired
    private GoodsCompanyMapper goodCompanyMapper;
    @Autowired
    private GoodsSupplierMapper goodSupplierMapper;
    @Autowired
    private GoodsManagementMapper goodManagementMapper;

    @Override
    public List<GoodsDetailedInformationDto> searchGoodDetailedInformationsByCondition(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            List<GoodsDetailedInformationDto> dtoList = new ArrayList<>();
            QueryWrapper<GoodsDetailedInformation> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id_delete", 0);
            queryWrapper.eq("good_categos_id", goodsDetailedInformationDto.getGoodCategosId());
            queryWrapper.eq("good_brands_id", goodsDetailedInformationDto.getGoodBrandsId());
            queryWrapper.eq("goods_tables_id", goodsDetailedInformationDto.getGoodsTablesId());
            queryWrapper.eq("good_supplier_id", goodsDetailedInformationDto.getGoodSupplierId());
            queryWrapper.eq("good_state", goodsDetailedInformationDto.getGoodState());
            queryWrapper.eq("good_grade_id", goodsDetailedInformationDto.getGoodGradeId());
            queryWrapper.and(i -> i.eq("good_tiao_code", goodsDetailedInformationDto.getGoodTiaoCode()).or().eq("good_name", goodsDetailedInformationDto.getGoodName()));
            List<GoodsDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
            if (list.size() < 0) {
                String exceptionMsg = String.format("商品列表异常, 未找到任何商品, 参数list: %s", list);
                throw new BusinessException(exceptionMsg);
            }
            list.forEach((bean) -> {
                GoodsDetailedInformationDto dto = new GoodsDetailedInformationDto();
                //单位
                QueryWrapper<GoodsCompany> companyWrapper = new QueryWrapper();
                companyWrapper.eq("id", bean.getGoodCompanyId());
                dto.setGoodCompay(goodCompanyMapper.selectOne(companyWrapper).getGoodCompany());
                //供应商
                QueryWrapper<GoodsSupplier> supplierWrapper = new QueryWrapper<>();
                supplierWrapper.eq("id", bean.getGoodSupplierId());
                dto.setGoodSuppliers(goodSupplierMapper.selectOne(supplierWrapper).getSupplier());
                //类别
                QueryWrapper<GoodsManagement> wrapper = new QueryWrapper<>();
                wrapper.eq("id", bean.getGoodCategosId());
                dto.setGoodCategos(goodManagementMapper.selectOne(wrapper).getName());
                //品牌
                wrapper.eq("id", bean.getGoodBrandsId());
                dto.setGoodBrand(goodManagementMapper.selectOne(wrapper).getName());
                //系列
                wrapper.eq("id", bean.getGoodsTablesId());
                dto.setGoodTable(goodManagementMapper.selectOne(wrapper).getName());
                BeanUtils.copyProperties(bean, dto);
                dtoList.add(dto);
            });
            return dtoList;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createGoodDetailedInformation(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            QueryWrapper<GoodsDetailedInformation> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_name", goodsDetailedInformationDto.getGoodName());
            queryWrapper.eq("store_id", goodsDetailedInformationDto.getStoreId());
            queryWrapper.notIn("id", goodsDetailedInformationDto.getId());
            List<GoodsDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                if (list.size() < 0) {
                    String exceptionMsg = String.format("商品添加异常, 商品名称已存在, 参数list: %s", list);
                    throw new BusinessException(exceptionMsg);
                }
            }
            GoodsDetailedInformation goodDetailedInformation = new GoodsDetailedInformation();
            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
            if (goodDetailedInformationMapper.insert(goodDetailedInformation) < 0) {
                String exceptionMsg = String.format("商品添加异常, 商品添加失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            QueryWrapper<GoodsDetailedInformation> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            queryWrapper.eq("good_name", goodsDetailedInformationDto.getGoodName());
            queryWrapper.eq("store_id", goodsDetailedInformationDto.getStoreId());
            queryWrapper.notIn("id", goodsDetailedInformationDto.getId());
            List<GoodsDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
            if (list.size() > 0) {
                if (list.size() < 0) {
                    String exceptionMsg = String.format("商品修改异常, 商品名称已存在, 参数list: %s", list);
                    throw new BusinessException(exceptionMsg);
                }
            }
            GoodsDetailedInformation goodDetailedInformation = new GoodsDetailedInformation();
            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
            if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
                String exceptionMsg = String.format("商品修改异常, 商品修改失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            GoodsDetailedInformation goodDetailedInformation = new GoodsDetailedInformation();
            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
            goodDetailedInformation.setIsDelete(1);
            if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
                String exceptionMsg = String.format("商品删除异常, 商品删除失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateGoodDetailedInformations(List<GoodsDetailedInformationDto> list) {
        try {
            List<GoodsDetailedInformation> array = BeanUtils.converteToDtoArray(list, GoodsDetailedInformation.class);
            for (GoodsDetailedInformation goodDetailedInformation : array) {
                if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
                    String exceptionMsg = String.format("商品列表批量修改异常, 商品列表批量修改失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
                    throw new BusinessException(exceptionMsg);
                }
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }
}
