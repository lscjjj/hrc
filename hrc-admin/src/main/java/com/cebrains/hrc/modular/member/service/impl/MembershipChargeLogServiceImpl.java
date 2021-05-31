package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.persistence.model.MembershipChargeLog;
import com.cebrains.hrc.common.persistence.dao.MembershipChargeLogMapper;
import com.cebrains.hrc.modular.member.service.IMembershipChargeLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员卡充值记录 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-04-17
 */
@Service
public class MembershipChargeLogServiceImpl extends ServiceImpl<MembershipChargeLogMapper, MembershipChargeLog> implements IMembershipChargeLogService {

    @Autowired
    MembershipChargeLogMapper membershipChargeLogMapper;

    @Override
    public List<MembershipChargeLog> selectThisDept(Integer depId) {
        return membershipChargeLogMapper.selectThisDept(depId);
    }
}
