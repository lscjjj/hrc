package com.cebrains.hrc.modular.member.service;


import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberHealthConsultation;

import java.util.List;

/**
 * 健康咨询 服务类
 */
public interface IMemberHealthConsultationService extends IService<MemberHealthConsultation> {

    List<MemberHealthConsultation> selectByThisDept(Integer depId);
}
