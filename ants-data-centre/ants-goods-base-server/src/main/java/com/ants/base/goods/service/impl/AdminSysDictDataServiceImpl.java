package com.ants.base.goods.service.impl;


import com.ants.base.goods.entity.AdminSysDictDataEntity;
import com.ants.base.goods.mapper.AdminSysDictDataMapper;
import com.ants.dubbo.api.base.product.AdminSysDictDataService;
import com.ants.module.goods.base.dto.AdminSysDictDataDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService
public class AdminSysDictDataServiceImpl implements AdminSysDictDataService {

    @Resource
    private AdminSysDictDataMapper adminSysDictDataMapper;

    @Override
    public AdminSysDictDataDto getAdminSysDictDataEntity(Integer id) {
        try {
            if (id == null) {
                String exceptionMsg = String.format("字典异常, 参数不正确, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            AdminSysDictDataEntity adminSysDictDataEntity = adminSysDictDataMapper.selectById(id);
            if (adminSysDictDataEntity == null) {
                String exceptionMsg = String.format("字典异常, 未找到字典数据, 参数id: %s", id);
                throw new BusinessException(exceptionMsg);
            }
            AdminSysDictDataDto adminSysDictDataDto = new AdminSysDictDataDto();
            BeanUtils.copyProperties(adminSysDictDataEntity, adminSysDictDataDto);
            return adminSysDictDataDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAdminSysDictDataEntityByStoreId(AdminSysDictDataDto adminSysDictDataDto) {
        try {
            AdminSysDictDataEntity adminSysDictDataEntity = new AdminSysDictDataEntity();
            BeanUtils.copyProperties(adminSysDictDataDto, adminSysDictDataEntity);
            if (adminSysDictDataMapper.updateById(adminSysDictDataEntity) < 0) {
                String exceptionMsg = String.format("字典异常, 字典修改失败, 参数adminSysDictDataEntity: %s", adminSysDictDataEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertAdminSysDictDataEntityByStoreId(AdminSysDictDataDto adminSysDictDataDto) {
        try {
            AdminSysDictDataEntity adminSysDictDataEntity = new AdminSysDictDataEntity();
            BeanUtils.copyProperties(adminSysDictDataDto, adminSysDictDataEntity);
            if (adminSysDictDataMapper.insert(adminSysDictDataEntity) < 0) {
                String exceptionMsg = String.format("字典异常, 字典添加失败, 参数adminSysDictDataEntity: %s", adminSysDictDataEntity);
                throw new BusinessException(exceptionMsg);
            }
            return true;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return false;
    }

    @Override
    public List<AdminSysDictDataDto> listAdminSysDictDataEntityByStoreId(String dictType) {
        try {
            if (dictType == null) {
                String exceptionMsg = String.format("字典异常, 参数不正确, 参数dictType: %s", dictType);
                throw new BusinessException(exceptionMsg);
            }
            QueryWrapper<AdminSysDictDataEntity> queryWrappe = new QueryWrapper();
            queryWrappe.eq("dict_type", dictType);
            queryWrappe.eq("is_delete", 0);
            List<AdminSysDictDataEntity> adminSysDictDataEntities = adminSysDictDataMapper.selectList(queryWrappe);
            if (adminSysDictDataEntities == null) {
                String exceptionMsg = String.format("字典异常, 未找到字典类型, 参数dictType: %s", dictType);
                throw new BusinessException(exceptionMsg);
            }
            List<AdminSysDictDataDto> dataDtoList = BeanUtils.converteToDtoArray(adminSysDictDataEntities, AdminSysDictDataDto.class);
            return dataDtoList;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAdminSysDictDataEntity(Integer id) {
        return false;
    }
}