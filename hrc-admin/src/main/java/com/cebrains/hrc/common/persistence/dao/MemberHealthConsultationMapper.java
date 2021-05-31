package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthConsultation;
import com.cebrains.hrc.common.persistence.model.MemberHealthRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 健康咨询 mapper接口
 */
public interface MemberHealthConsultationMapper extends BaseMapper<MemberHealthConsultation> {

    @Select("SELECT \n" +
            "mhc.id,\n" +
            "mhc.member_user memberUser,\n" +
            "mhc.family,\n" +
            "mhc.department,\n" +
            "mhc.symptoms,\n" +
            "mhc.consultation_info consultationInfo,\n" +
            "mhc.created_by createdBy,\n" +
            "mhc.consultation_suggest consultationSuggest,\n" +
            "mhc.suggest_produce suggestProduce,\n" +
            "mhc.state,\n" +
            "mhc.create_time createTime\n" +
            "FROM `member_health_consultation` mhc left JOIN sys_user su on su.id = mhc.member_user where su.deptid = #{depId}")
    List<MemberHealthConsultation> selectByThisDept(Integer depId);

}
