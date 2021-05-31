package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberHealthSurveyMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import com.cebrains.hrc.modular.member.service.IMemberHealthSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 健康信息管理 服务实现类
 */
@Service
public class MemberHealthSurveyServiceImpl extends ServiceImpl<MemberHealthSurveyMapper, MemberHealthSurvey> implements IMemberHealthSurveyService {

    @Autowired
    MemberHealthSurveyMapper memberHealthSurveyMapper;

    @Override
    public List<MemberHealthSurvey> selectByThisDept(Integer depId) {
        return memberHealthSurveyMapper.selectByThisDept(depId);
    }
}
