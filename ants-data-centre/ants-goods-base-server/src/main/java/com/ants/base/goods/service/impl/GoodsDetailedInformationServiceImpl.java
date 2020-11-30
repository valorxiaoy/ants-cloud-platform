//package com.ants.base.goods.service.impl;
//
//import com.ants.base.goods.entity.GoodCompany;
//import com.ants.base.goods.entity.GoodDetailedInformation;
//import com.ants.base.goods.entity.GoodManagement;
//import com.ants.base.goods.entity.GoodSupplier;
//import com.ants.base.goods.mapper.*;
//import com.ants.dubbo.api.base.goods.IGoodDetailedInformationService;
//import com.ants.module.goods.base.dto.GoodsDetailedInformationDto;
//import com.ants.tools.exception.BusinessException;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboService;
//import com.ants.tools.utils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * @author 小米
// * @date 2020-11-19
// */
//@Slf4j
//@DubboService
//public class GoodDetailedInformationServiceImpl extends ServiceImpl
//        <GoodDetailedInformationMapper, GoodDetailedInformation> implements IGoodDetailedInformationService {
//
//    @Autowired
//    private GoodDetailedInformationMapper goodDetailedInformationMapper;
//    @Autowired
//    private GoodCompanyMapper goodCompanyMapper;
//    @Autowired
//    private GoodSupplierMapper goodSupplierMapper;
//    @Autowired
//    private GoodManagementMapper goodManagementMapper;
//
//    @Override
//    public List<GoodsDetailedInformationDto> searchGoodDetailedInformationsByCondition(GoodsDetailedInformationDto goodsDetailedInformationDto) {
//        try {
//            List<GoodsDetailedInformationDto> dtoList = new ArrayList<>();
//            QueryWrapper<GoodDetailedInformation> queryWrapper = new QueryWrapper();
//            queryWrapper.eq("id_delete", 0);
//            queryWrapper.eq("good_categos_id", goodsDetailedInformationDto.getGoodCategosId());
//            queryWrapper.eq("good_brands_id", goodsDetailedInformationDto.getGoodBrandsId());
//            queryWrapper.eq("goods_tables_id", goodsDetailedInformationDto.getGoodsTablesId());
//            queryWrapper.eq("good_supplier_id", goodsDetailedInformationDto.getGoodSupplierId());
//            queryWrapper.eq("good_state", goodsDetailedInformationDto.getGoodState());
//            queryWrapper.eq("good_grade_id", goodsDetailedInformationDto.getGoodGradeId());
//            queryWrapper.and(i -> i.eq("good_tiao_code", goodsDetailedInformationDto.getGoodTiaoCode()).or().eq("good_name", goodsDetailedInformationDto.getGoodName()));
//            List<GoodDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
//            if (list.size() < 0) {
//                String exceptionMsg = String.format("商品列表异常, 未找到任何商品, 参数list: %s", list);
//                throw new BusinessException(exceptionMsg);
//            }
//            list.forEach((bean) -> {
//                GoodsDetailedInformationDto dto = new GoodsDetailedInformationDto();
//                //单位
//                QueryWrapper<GoodCompany> companyWrapper = new QueryWrapper();
//                companyWrapper.eq("id", bean.getGoodCompanyId());
//                dto.setGoodCompay(goodCompanyMapper.selectOne(companyWrapper).getGoodCompany());
//                //供应商
//                QueryWrapper<GoodSupplier> supplierWrapper = new QueryWrapper<>();
//                supplierWrapper.eq("id", bean.getGoodSupplierId());
//                dto.setGoodSuppliers(goodSupplierMapper.selectOne(supplierWrapper).getSupplier());
//                //类别
//                QueryWrapper<GoodManagement> wrapper = new QueryWrapper<>();
//                wrapper.eq("id", bean.getGoodCategosId());
//                dto.setGoodCategos(goodManagementMapper.selectOne(wrapper).getName());
//                //品牌
//                wrapper.eq("id", bean.getGoodBrandsId());
//                dto.setGoodBrand(goodManagementMapper.selectOne(wrapper).getName());
//                //系列
//                wrapper.eq("id", bean.getGoodsTablesId());
//                dto.setGoodTable(goodManagementMapper.selectOne(wrapper).getName());
//                BeanUtils.copyProperties(bean, dto);
//                dtoList.add(dto);
//            });
//            return dtoList;
//        } catch (BusinessException businessException) {
//            businessException.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public boolean createGoodDetailedInformation(GoodsDetailedInformationDto goodsDetailedInformationDto) {
//        try {
//            QueryWrapper<GoodDetailedInformation> queryWrapper = new QueryWrapper();
//            queryWrapper.eq("is_delete", 0);
//            queryWrapper.eq("good_name", goodsDetailedInformationDto.getGoodName());
//            queryWrapper.eq("store_id", goodsDetailedInformationDto.getStoreId());
//            queryWrapper.notIn("id", goodsDetailedInformationDto.getId());
//            List<GoodDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
//            if (list.size() > 0) {
//                if (list.size() < 0) {
//                    String exceptionMsg = String.format("商品添加异常, 商品名称已存在, 参数list: %s", list);
//                    throw new BusinessException(exceptionMsg);
//                }
//            }
//            GoodDetailedInformation goodDetailedInformation = new GoodDetailedInformation();
//            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
//            if (goodDetailedInformationMapper.insert(goodDetailedInformation) < 0) {
//                String exceptionMsg = String.format("商品添加异常, 商品添加失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
//                throw new BusinessException(exceptionMsg);
//            }
//            return true;
//        } catch (BusinessException businessException) {
//            businessException.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto) {
//        try {
//            QueryWrapper<GoodDetailedInformation> queryWrapper = new QueryWrapper();
//            queryWrapper.eq("is_delete", 0);
//            queryWrapper.eq("good_name", goodsDetailedInformationDto.getGoodName());
//            queryWrapper.eq("store_id", goodsDetailedInformationDto.getStoreId());
//            queryWrapper.notIn("id", goodsDetailedInformationDto.getId());
//            List<GoodDetailedInformation> list = goodDetailedInformationMapper.selectList(queryWrapper);
//            if (list.size() > 0) {
//                if (list.size() < 0) {
//                    String exceptionMsg = String.format("商品修改异常, 商品名称已存在, 参数list: %s", list);
//                    throw new BusinessException(exceptionMsg);
//                }
//            }
//            GoodDetailedInformation goodDetailedInformation = new GoodDetailedInformation();
//            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
//            if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
//                String exceptionMsg = String.format("商品修改异常, 商品修改失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
//                throw new BusinessException(exceptionMsg);
//            }
//            return true;
//        } catch (BusinessException businessException) {
//            businessException.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteGoodDetailedInformations(GoodsDetailedInformationDto goodsDetailedInformationDto) {
//        try {
//            GoodDetailedInformation goodDetailedInformation = new GoodDetailedInformation();
//            BeanUtils.copyProperties(goodsDetailedInformationDto, goodDetailedInformation);
//            goodDetailedInformation.setIsDelete(1);
//            if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
//                String exceptionMsg = String.format("商品删除异常, 商品删除失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
//                throw new BusinessException(exceptionMsg);
//            }
//            return true;
//        } catch (BusinessException businessException) {
//            businessException.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean batchUpdateGoodDetailedInformations(List<GoodsDetailedInformationDto> list) {
//        try {
//            List<GoodDetailedInformation> array = BeanUtils.converteToDtoArray(list, GoodDetailedInformation.class);
//            for (GoodDetailedInformation goodDetailedInformation : array) {
//                if (goodDetailedInformationMapper.updateById(goodDetailedInformation) < 0) {
//                    String exceptionMsg = String.format("商品列表批量修改异常, 商品列表批量修改失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
//                    throw new BusinessException(exceptionMsg);
//                }
//            }
//            return true;
//        } catch (BusinessException businessException) {
//            businessException.printStackTrace();
//            return false;
//        }
//    }
//}
