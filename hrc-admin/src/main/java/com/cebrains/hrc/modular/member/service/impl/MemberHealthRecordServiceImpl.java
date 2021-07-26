package com.cebrains.hrc.modular.member.service.impl;

import com.cebrains.hrc.common.persistence.model.MemberHealthRecord;
import com.cebrains.hrc.common.persistence.dao.MemberHealthRecordMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import com.cebrains.hrc.modular.member.service.IMemberHealthRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员健康档案 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-07-02
 */
@Service
public class MemberHealthRecordServiceImpl extends ServiceImpl<MemberHealthRecordMapper, MemberHealthRecord> implements IMemberHealthRecordService {

    @Autowired
    MemberHealthRecordMapper memberHealthRecordMapper;

    @Override
    public List<MemberHealthRecord> selectByThisDept(Integer depId) {
        return memberHealthRecordMapper.selectByThisDept(depId);
    }

    @Override
    public List<Map<String, Object>> selectAll() {
        return memberHealthRecordMapper.selectAll();
    }

    @Override
    public List<Map<String, Object>> selectByName(String key) {
        return memberHealthRecordMapper.selectByName(key);
    }

    @Override
    public List<Map<String, Object>> selectAllByDep(Integer depId) {
        return memberHealthRecordMapper.selectAllByDep(depId);
    }

    @Override
    public List<Map<String, Object>> selectAllToAdd() {
        return memberHealthRecordMapper.selectAllToAdd();
    }

}
