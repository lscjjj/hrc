package com.cebrains.hrc.modular.station.service.impl;

import com.cebrains.hrc.common.persistence.model.RehabilitationProgram;
import com.cebrains.hrc.common.persistence.dao.RehabilitationProgramMapper;
import com.cebrains.hrc.modular.station.service.IRehabilitationProgramService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 康复方案 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-07-04
 */
@Service
public class RehabilitationProgramServiceImpl extends ServiceImpl<RehabilitationProgramMapper, RehabilitationProgram> implements IRehabilitationProgramService {

    @Autowired
    RehabilitationProgramMapper rehabilitationProgramMapper;

    @Override
    public List<RehabilitationProgram> selectByThisDept(Integer depId) {
        return rehabilitationProgramMapper.selectByThisDept(depId);
    }

    @Override
    public List<Map<String, Object>> selectAll() {
        return rehabilitationProgramMapper.selectAll();
    }

    @Override
    public List<Map<String, Object>> selectByName(String key) {
        return rehabilitationProgramMapper.selectByName(key);
    }
}
