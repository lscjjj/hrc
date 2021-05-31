package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;

import java.util.List;

/**
 * 健康管理 服务类
 */
public interface IMemberHealthSurveyService extends IService<MemberHealthSurvey> {

    List<MemberHealthSurvey> selectByThisDept(Integer depId);
}
