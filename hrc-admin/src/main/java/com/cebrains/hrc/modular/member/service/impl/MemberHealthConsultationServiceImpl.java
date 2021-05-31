package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberHealthConsultationMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthConsultation;
import com.cebrains.hrc.modular.member.service.IMemberHealthConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 健康咨询 服务实现类
 */
@Service
public class MemberHealthConsultationServiceImpl extends ServiceImpl<MemberHealthConsultationMapper, MemberHealthConsultation> implements IMemberHealthConsultationService {

    @Autowired
    MemberHealthConsultationMapper memberHealthConsultationMapper;

    @Override
    public List<MemberHealthConsultation> selectByThisDept(Integer depId) {
        return memberHealthConsultationMapper.selectByThisDept(depId);
    }
}
