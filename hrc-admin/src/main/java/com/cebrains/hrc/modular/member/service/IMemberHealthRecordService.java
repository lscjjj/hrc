package com.cebrains.hrc.modular.member.service;

import com.cebrains.hrc.common.persistence.model.MemberHealthRecord;
import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员健康档案 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-07-02
 */
public interface IMemberHealthRecordService extends IService<MemberHealthRecord> {

    List<MemberHealthRecord> selectByThisDept(Integer depId);

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> selectByName(String key);

    List<Map<String,Object>> selectAllByDep(Integer depId);

    List<Map<String,Object>> selectAllToAdd();

}
