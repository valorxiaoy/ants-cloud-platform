package com.ants.base.store.service.impl;

import com.ants.base.store.entity.SysStore;
import com.ants.base.store.mapper.SysStoreMapper;
import com.ants.dubbo.api.base.store.IStoreService;
import com.ants.module.store.SysStoreDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 门店信息服务
 *
 * @author Yueyang
 * @create 2020-11-16 23:30
 **/
@Slf4j
@DubboService
public class StoreServiceImpl implements IStoreService {

    @Autowired
    private SysStoreMapper sysStoreMapper;

    @Override
    public SysStoreDto searchSysStore(Integer storeId) {
        try {
            SysStore sysStore = sysStoreMapper.selectById(storeId);
            if (sysStore == null) {
                String exceptionMsg = String.format("门店基础信息异常, 未找到门店, 参数storeId: %s", storeId);
                throw new BusinessException(exceptionMsg);
            }
            SysStoreDto sysStoreDto = new SysStoreDto();
            BeanUtils.copyBeanProp(sysStoreDto, sysStore);
            return sysStoreDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public SysStoreDto searchMasterStoreByBranchStoreId(Integer storeId) {
        try {
            SysStore sysStore = sysStoreMapper.selectById(storeId);
            if (sysStore == null) {
                String exceptionMsg = String.format("门店基础信息异常, 未找到所属门店, 参数storeId: %s", storeId);
                throw new BusinessException(exceptionMsg);
            }

            Integer belongingStore = sysStore.getBelongingStore();
            if (belongingStore == null) {
                String exceptionMsg = String.format("门店基础信息异常, 未找到所属门店, 参数belongingStore: %s", belongingStore);
                throw new BusinessException(exceptionMsg);
            }

            SysStoreDto sysStoreDto;
            // belongingStore == 0,该门店是总店
            if (belongingStore == 0) {
                sysStoreDto = new SysStoreDto();
                BeanUtils.copyBeanProp(sysStoreDto, sysStore);
            } else {
                SysStore masterStore = sysStoreMapper.selectById(belongingStore);
                if (masterStore == null) {
                    String exceptionMsg = String.format("总店信息异常, 未找到门店信息, 参数belongingStore: %s", belongingStore);
                    throw new BusinessException(exceptionMsg);
                }
                sysStoreDto = new SysStoreDto();
                BeanUtils.copyBeanProp(sysStoreDto, masterStore);
            }
            return sysStoreDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }
}