package com.ants.member.service;

import com.ants.dubbo.api.base.member.IMemberBaseService;
import com.ants.dubbo.api.service.member.IMemberSerivce;
import com.ants.module.member.UmsMemberDto;

import javax.annotation.Resource;

/**
 * @program: ants-cloud-platform
 * @description: 会员服务
 * @author: 刘智
 * @create: 2020-12-02 14:04
 **/
public class MemberServiceImpl implements IMemberSerivce {

    @Resource
    private IMemberBaseService iMemberBaseService;

    @Override
    public boolean memberAddIntegral(UmsMemberDto umsMemberDto, Integer integral) {
        if (umsMemberDto == null) {
            String exceptionMsg = String.format("会员增加积分, 会员对象为空, 参数umsMemberDto: %s", umsMemberDto);
            return false;
        }
        if (integral == null) {
            String exceptionMsg = String.format("会员增加积分, 积分为空, 参数integral: %s", integral);
            return false;
        }
        umsMemberDto.setAvailableIntegral(umsMemberDto.getAvailableIntegral() + integral);
        return iMemberBaseService.updateUmsMember(umsMemberDto);
    }

    @Override
    public boolean memberAddIntegral(Integer umsMemberId, Integer integral) {
        UmsMemberDto umsMemberDto = iMemberBaseService.searchUmsMember(umsMemberId);
        if (umsMemberDto == null) {
            String exceptionMsg = String.format("会员增加积分, 会员对象为空, 参数umsMemberDto: %s", umsMemberDto);
            return false;
        }
        if (integral == null) {
            String exceptionMsg = String.format("会员增加积分, 积分为空, 参数integral: %s", integral);
            return false;
        }
        umsMemberDto.setAvailableIntegral(umsMemberDto.getAvailableIntegral() + integral);
        return iMemberBaseService.updateUmsMember(umsMemberDto);
    }

    @Override
    public boolean memberReduceIntegral(UmsMemberDto umsMemberDto, Integer integral) {
        if (umsMemberDto == null) {
            String exceptionMsg = String.format("会员减少积分, 会员对象为空, 参数umsMemberDto: %s", umsMemberDto);
            return false;
        }
        if (integral == null) {
            String exceptionMsg = String.format("会员减少积分, 积分为空, 参数integral: %s", integral);
            return false;
        }
        if (integral < umsMemberDto.getAvailableIntegral()) {
            String exceptionMsg = String.format("会员减少积分, 积分小于余额, 参数integral: %s", integral);
            return false;
        }
        umsMemberDto.setAvailableIntegral(umsMemberDto.getAvailableIntegral() - integral);
        return iMemberBaseService.updateUmsMember(umsMemberDto);
    }

    @Override
    public boolean memberReduceIntegral(Integer umsMemberId, Integer integral) {
        UmsMemberDto umsMemberDto = iMemberBaseService.searchUmsMember(umsMemberId);
        if (umsMemberDto == null) {
            String exceptionMsg = String.format("会员减少积分, 会员对象为空, 参数umsMemberDto: %s", umsMemberDto);
            return false;
        }
        if (integral == null) {
            String exceptionMsg = String.format("会员减少积分, 积分为空, 参数integral: %s", integral);
            return false;
        }
        if (integral < umsMemberDto.getAvailableIntegral()) {
            String exceptionMsg = String.format("会员减少积分, 积分小于余额, 参数integral: %s", integral);
            return false;
        }
        umsMemberDto.setAvailableIntegral(umsMemberDto.getAvailableIntegral() - integral);
        return iMemberBaseService.updateUmsMember(umsMemberDto);
    }
}
