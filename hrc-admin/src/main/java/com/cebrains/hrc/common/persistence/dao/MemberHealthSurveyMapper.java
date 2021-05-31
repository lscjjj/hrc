package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberHealthSurveyMapper extends BaseMapper<MemberHealthSurvey> {

    @Select("SELECT " +
            "mhs.id," +
            "mhs.member," +
            "mhs.age," +
            "mhs.survey_info surveyInfo " +
            ",mhs.department," +
            "mhs.remark," +
            "mhs.created_by createdBy," +
            "mhs.create_time createTime " +
            "FROM `member_health_survey` mhs left join member m on mhs.member = m.id where m.clinic = #{depId}")
    List<MemberHealthSurvey> selectByThisDept(Integer depId);
}
