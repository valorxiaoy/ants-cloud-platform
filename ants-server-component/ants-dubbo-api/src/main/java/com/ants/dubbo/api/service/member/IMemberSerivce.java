package com.ants.dubbo.api.service.member;

import com.ants.module.member.UmsMemberDto;

/**
 * @author CheapMan_Z
 */
public interface IMemberSerivce {
    /**
     * 描述： 会员增加积分方法
     *
     * @param umsMemberDto: 会员Dto对象
     * @param integral:     需要增加的积分
     * @return boolean
     * @Author: 刘智
     */
    boolean memberAddIntegral(UmsMemberDto umsMemberDto, Integer integral);

    /**
     * 描述：会员减少积分方法
     *
     * @param umsMemberDto: 会员Dto对象
     * @param integral:     需要减少的积分
     * @return boolean
     * @Author: 刘智
     */
    boolean memberReduceIntegral(UmsMemberDto umsMemberDto, Integer integral);
}
