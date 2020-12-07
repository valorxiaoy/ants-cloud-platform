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
//    @Resource
//    private IGoodsInoutDetailsService iGoodsInoutDetailsService;

    @Override
    public List<GoodsDetailedInformationDto> searchGoodsDetailedInformationsByCondition(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            List<GoodsDetailedInformationDto> dtoList = new ArrayList<>();
            QueryWrapper<GoodsDetailedInformation> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id_delete", 0);
            //类别
            if (goodsDetailedInformationDto.getGoodCategos() != null) {
                queryWrapper.eq("good_categos_id", goodsDetailedInformationDto.getGoodCategosId());
            }
            //品牌
            if (goodsDetailedInformationDto.getGoodBrand() != null) {
                queryWrapper.eq("good_brands_id", goodsDetailedInformationDto.getGoodBrandsId());
            }
            //系列
            if (goodsDetailedInformationDto.getGoodTable() != null) {
                queryWrapper.eq("goods_tables_id", goodsDetailedInformationDto.getGoodsTablesId());
            }
            //供应商
            if (goodsDetailedInformationDto.getGoodSupplierId() != null) {
                queryWrapper.eq("good_supplier_id", goodsDetailedInformationDto.getGoodSupplierId());
            }
            //状态
            if (goodsDetailedInformationDto.getGoodState() != null) {
                queryWrapper.eq("good_state", goodsDetailedInformationDto.getGoodState());
            }
            //上下架状态
            if (goodsDetailedInformationDto.getIfState() != null) {
                queryWrapper.eq("if_state", goodsDetailedInformationDto.getIfState());
            }
            //等级
            if (goodsDetailedInformationDto.getGoodGradeId() != null) {
                queryWrapper.eq("good_grade_id", goodsDetailedInformationDto.getGoodGradeId());
            }
            //多条件
            if (goodsDetailedInformationDto.getKeyword() != null) {
                queryWrapper.and(i -> i.eq("good_tiao_code", goodsDetailedInformationDto.getGoodTiaoCode()).or().eq("good_name", goodsDetailedInformationDto.getGoodName()));
            }
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
    public GoodsDetailedInformationDto searchGoodsDetailedInformationsById(Integer id) {
        GoodsDetailedInformationDto dto = new GoodsDetailedInformationDto();
        GoodsDetailedInformation goodsDetailedInformation = goodDetailedInformationMapper.selectById(id);
        //单位
        QueryWrapper<GoodsCompany> companyWrapper = new QueryWrapper();
        companyWrapper.eq("id", goodsDetailedInformation.getGoodCompanyId());
        dto.setGoodCompay(goodCompanyMapper.selectOne(companyWrapper).getGoodCompany());
        //供应商
        QueryWrapper<GoodsSupplier> supplierWrapper = new QueryWrapper<>();
        supplierWrapper.eq("id", goodsDetailedInformation.getGoodSupplierId());
        dto.setGoodSuppliers(goodSupplierMapper.selectOne(supplierWrapper).getSupplier());
        //类别
        QueryWrapper<GoodsManagement> wrapper = new QueryWrapper<>();
        wrapper.eq("id", goodsDetailedInformation.getGoodCategosId());
        dto.setGoodCategos(goodManagementMapper.selectOne(wrapper).getName());
        //品牌
        wrapper.eq("id", goodsDetailedInformation.getGoodBrandsId());
        dto.setGoodBrand(goodManagementMapper.selectOne(wrapper).getName());
        //系列
        wrapper.eq("id", goodsDetailedInformation.getGoodsTablesId());
        dto.setGoodTable(goodManagementMapper.selectOne(wrapper).getName());
        BeanUtils.copyProperties(goodsDetailedInformation, dto);
        return dto;
    }

    @Override
    public boolean createGoodsDetailedInformationByStoreId(GoodsDetailedInformationDto goodsDetailedInformationDto) {
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
//            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//
//            GoodsInoutDetailsDto goodsInoutDetailsDto = new GoodsInoutDetailsDto();
//
//            //订单号
//            String orderSn = "CJ" + time.format(new Date());
//            //商品条码
//            goodsInoutDetailsDto.setGoodTiaoCode(goodDetailedInformation.getGoodTiaoCode());
//            //商品名称
//            goodsInoutDetailsDto.setGoodName(goodDetailedInformation.getGoodName());
//            //入库数量
//            goodsInoutDetailsDto.setInboundQuantity(goodDetailedInformation.getInitialStock());
//            //出库数量
//            goodsInoutDetailsDto.setOutOfStock(0);
//            // 类型，0出库，1入库
//            goodsInoutDetailsDto.setLibraryType(1);
//            //来源：0网络订单，1供应商，2门店调拨，3门店销售  4商品损益调整 5盘点差异 6execl导入 7商品添加
//            goodsInoutDetailsDto.setSource(7);
//            //审核状态状态，0未审核，1已审核
//            goodsInoutDetailsDto.setStatus(1);
//            //进货价
//            goodsInoutDetailsDto.setPurchasePrice(goodDetailedInformation.getGoodInprice());
//            //进货金额
//            if (goodDetailedInformation.getInitialStock() == 0) {
//                goodsInoutDetailsDto.setPurchaseAmount(new BigDecimal(0));
//            } else {
//                goodsInoutDetailsDto.setPurchaseAmount(goodDetailedInformation.getGoodInprice().multiply(new BigDecimal(goodDetailedInformation.getInDistribution())));
//            }
//            //门店id
//            goodsInoutDetailsDto.setStoreId(goodDetailedInformation.getStoreId());
//            //审核时间
//            goodsInoutDetailsDto.setReviewedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//
//            List<GoodsInoutDetailsDto> dtoList = new ArrayList<>();
//
//            dtoList.add(goodsInoutDetailsDto);
//
//            if (iGoodsInoutDetailsService.createGoodsInoutDetails(dtoList, orderSn)) {
//                String exceptionMsg = String.format("商品添加异常, 商品添加入库明细单失败, 参数goodDetailedInformation: %s", goodDetailedInformation);
//                throw new BusinessException(exceptionMsg);
//            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGoodsDetailedInformationsByStoreId(GoodsDetailedInformationDto goodsDetailedInformationDto) {
        try {
            QueryWrapper<GoodsDetailedInformation> queryWrapper = new QueryWrapper();
            queryWrapper.eq("is_delete", 0);
            //商品名称
            if (goodsDetailedInformationDto.getGoodName() != null) {
                queryWrapper.eq("good_name", goodsDetailedInformationDto.getGoodName());
            }
            //门店
            if (goodsDetailedInformationDto.getStoreId() != null) {
                queryWrapper.eq("store_id", goodsDetailedInformationDto.getStoreId());
            }
            //id
            if (goodsDetailedInformationDto.getId() != null) {
                queryWrapper.notIn("id", goodsDetailedInformationDto.getId());
            }
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
    public boolean deleteGoodsDetailedInformationsById(Integer id) {
        try {
            GoodsDetailedInformation goodDetailedInformation = new GoodsDetailedInformation();
            goodDetailedInformation.setId(id);
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
    public boolean batchUpdateGoodsDetailedInformationsByStoreId(List<GoodsDetailedInformationDto> list) {
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
