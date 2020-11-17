package com.ants.dubbo.api.base.store;

import com.ants.module.store.SysStoreDto;

/**
 * 门店信息服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IStoreService {

    /**
     * 根据ID查找门店信息
     *
     * @param storeId 门店ID
     * @return 门店对象
     */
    SysStoreDto searchSysStore(Integer storeId);

    /**
     * 根据分支门店ID查询总门店数据
     *
     * @param storeId 分支门店ID
     * @return 总门店对象
     */
    SysStoreDto searchMasterStoreByBranchStoreId(Integer storeId);
}
