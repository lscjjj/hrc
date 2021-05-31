package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberFamilyMapper;
import com.cebrains.hrc.common.persistence.model.MemberFamily;
import com.cebrains.hrc.modular.member.service.IMemberFamilyService;
import org.springframework.stereotype.Service;

/**
 * 家庭成员服务实现类
 */
@Service
public class MemberFamilyServiceImpl extends ServiceImpl<MemberFamilyMapper, MemberFamily> implements IMemberFamilyService {
}
