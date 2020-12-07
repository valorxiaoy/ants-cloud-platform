package com.ants.dubbo.api.base.member;

import com.ants.module.member.UmsMemberDto;
import com.ants.module.member.UmsMemberLevelDto;

/**
 * 会员信息服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IMemberBaseService {

    /**
     * 根据会员ID，查询会员信息
     *
     * @param memberId 会员ID
     * @return 会员对象
     */
    UmsMemberDto searchUmsMember(Integer memberId);

    /**
     * 根据会员ID，查询会员等级信息
     *
     * @param memberId 会员ID
     * @return 会员等级对象
     */
    UmsMemberLevelDto searchUmsMemberLevelByMemberId(Integer memberId);

    /**
     * 描述： 修改会员信息
     *
     * @param umsMemberDto:
     * @return boolean
     * @Author: 刘智
     */
    boolean updateUmsMember(UmsMemberDto umsMemberDto);
}
