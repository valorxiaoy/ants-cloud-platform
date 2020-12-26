package com.ants.base.goods.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.AdminSysDictDataDao;
import io.renren.modules.generator.entity.AdminSysDictDataEntity;
import io.renren.modules.generator.service.AdminSysDictDataService;


@Service("adminSysDictDataService")
public class AdminSysDictDataServiceImpl extends ServiceImpl<AdminSysDictDataDao, AdminSysDictDataEntity> implements AdminSysDictDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminSysDictDataEntity> page = this.page(
                new Query<AdminSysDictDataEntity>().getPage(params),
                new QueryWrapper<AdminSysDictDataEntity>()
        );

        return new PageUtils(page);
    }

}