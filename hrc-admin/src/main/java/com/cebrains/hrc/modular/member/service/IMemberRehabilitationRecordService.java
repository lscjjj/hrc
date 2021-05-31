package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;

import java.util.List;

/**
 * 康复记录服务类
 */
public interface IMemberRehabilitationRecordService extends IService<MemberRehabilitationRecord> {

    List<MemberRehabilitationRecord> list(Integer id);
}
