package com.cebrains.hrc.modular.station.service;

import com.cebrains.hrc.common.persistence.model.RehabilitationProgram;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 康复方案 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-07-04
 */
public interface IRehabilitationProgramService extends IService<RehabilitationProgram> {

    List<RehabilitationProgram> selectByThisDept(Integer depId);

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> selectByName(String key);
}
