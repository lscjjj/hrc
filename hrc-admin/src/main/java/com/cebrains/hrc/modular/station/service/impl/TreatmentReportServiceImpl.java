package com.cebrains.hrc.modular.station.service.impl;

import com.cebrains.hrc.common.persistence.model.TreatmentReport;
import com.cebrains.hrc.common.persistence.dao.TreatmentReportMapper;
import com.cebrains.hrc.modular.station.service.ITreatmentReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-10-09
 */
@Service
public class TreatmentReportServiceImpl extends ServiceImpl<TreatmentReportMapper, TreatmentReport> implements ITreatmentReportService {

    @Autowired
    TreatmentReportMapper treatmentReportMapper;

    @Override
    public List<TreatmentReport> selectThisDept(Integer depId) {
        return treatmentReportMapper.selectThisDept(depId);
    }
}
