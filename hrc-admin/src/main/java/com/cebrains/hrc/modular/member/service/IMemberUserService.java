package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberUser;

import java.util.List;

/**
 * 个人会员用户 服务类
 */
public interface IMemberUserService extends IService<MemberUser> {

    List<MemberUser> list(Integer id);
}
