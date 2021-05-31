package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberRehabilitationRecordMapper;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.modular.member.service.IMemberRehabilitationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 康复记录服务实现类
 */
@Service
public class MemberRehabilitationRecordServiceImpl extends ServiceImpl<MemberRehabilitationRecordMapper, MemberRehabilitationRecord> implements IMemberRehabilitationRecordService {

    @Autowired
    private MemberRehabilitationRecordMapper memberRehabilitationRecordMapper;

    public List<MemberRehabilitationRecord> list(Integer id) {
        return memberRehabilitationRecordMapper.list(id);
    }
}
