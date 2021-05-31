package com.cebrains.hrc.modular.station.service;

import com.cebrains.hrc.common.persistence.model.TreatmentReport;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author frank123
 * @since 2018-10-09
 */
public interface ITreatmentReportService extends IService<TreatmentReport> {

    List<TreatmentReport> selectThisDept(Integer depId);

}
