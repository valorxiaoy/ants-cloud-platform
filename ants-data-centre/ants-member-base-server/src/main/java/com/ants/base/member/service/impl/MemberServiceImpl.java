package com.ants.base.member.service.impl;

import com.ants.base.member.entity.UmsMember;
import com.ants.base.member.entity.UmsMemberLevel;
import com.ants.base.member.mapper.UmsMemberLevelMapper;
import com.ants.base.member.mapper.UmsMemberMapper;
import com.ants.dubbo.api.base.member.IMemberService;
import com.ants.module.member.UmsMemberDto;
import com.ants.module.member.UmsMemberLevelDto;
import com.ants.tools.exception.BusinessException;
import com.ants.tools.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员信息服务
 *
 * @author Yueyang
 * @create 2020-11-16 23:59
 **/
@Slf4j
@DubboService
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;

    @Override
    public UmsMemberDto searchUmsMember(Integer memberId) {
        try {
            UmsMember umsMember = umsMemberMapper.selectById(memberId);
            if (umsMember == null) {
                String exceptionMsg = String.format("会员基础信息异常, 未找到会员, 参数memberId: %s", memberId);
                throw new BusinessException(exceptionMsg);
            }
            UmsMemberDto umsMemberDto = new UmsMemberDto();
            BeanUtils.copyBeanProp(umsMemberDto, umsMember);
            return umsMemberDto;
        } catch (BusinessException businessException) {
            businessException.printStackTrace();
            return null;
        }
    }

    @Override
    public UmsMemberLevelDto searchUmsMemberLevelByMemberId(Integer memberId) {
        UmsMemberLevel umsMemberLevel = umsMemberLevelMapper.selectById(memberId);
        UmsMemberLevelDto umsMemberLevelDto = new UmsMemberLevelDto();
        BeanUtils.copyBeanProp(umsMemberLevelDto, umsMemberLevel);
        return umsMemberLevelDto;
    }
}