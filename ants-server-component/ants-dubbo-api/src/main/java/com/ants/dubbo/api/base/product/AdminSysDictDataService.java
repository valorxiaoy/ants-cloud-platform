package com.ants.dubbo.api.base.product;


import com.ants.module.goods.base.dto.AdminSysDictDataDto;

import java.util.List;

/**
 * 字典数据表
 *
 * @author 小米
 * @date 2020-12-26 16:34:50
 */
public interface AdminSysDictDataService {
    /**
     * 根据id 获取对应的字典
     *
     * @param id
     * @return 字典对象
     */
    AdminSysDictDataDto getAdminSysDictDataDto(Integer id);

    /**
     * 描述： 修改字典信息
     *
     * @param adminSysDictDataDto:
     * @return boolean
     */
    boolean updateAdminSysDictDataByStoreId(AdminSysDictDataDto adminSysDictDataDto);

    /**
     * 描述： 创建字典信息
     *
     * @param adminSysDictDataDto:
     * @return boolean
     */
    boolean insertAdminSysDictDataByStoreId(AdminSysDictDataDto adminSysDictDataDto);

    /**
     * 根据门店id 获取对应的字典
     *
     * @param dictType
     * @return 多个字典对象
     */
    List<AdminSysDictDataDto> searchAdminSysDictDataDtoByStoreId(String dictType);

    /**
     * 根据dictCode 删除对应的字典
     *
     * @param dictCode
     * @return boolean
     */
    boolean deleteAdminSysDictData(Long dictCode);
}

