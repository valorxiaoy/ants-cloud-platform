package com.ants.dubbo.api.base.member;

import com.ants.module.member.UmsMemberDto;

/**
 * 会员信息服务
 *
 * @author Yueyang
 * @create 2020-11-09 19:11
 **/
public interface IMemberService {

    /**
     * 根据ID查询会员信息
     * @param memberId 会员ID
     * @return 会员对象
     */
    UmsMemberDto searchUmsMember(Integer memberId);
}
