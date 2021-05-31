package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberUserMapper;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.modular.member.service.IMemberUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人会员信息 服务实现类
 */

@Service
public class MemberUserServiceImpl extends ServiceImpl<MemberUserMapper, MemberUser> implements IMemberUserService {

    @Autowired
    private MemberUserMapper userMapper;

    public List<MemberUser> list(Integer id) {
        return userMapper.list(id);
    }

}
