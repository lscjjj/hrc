package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.MemberHealthRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员健康档案 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-07-02
 */
public interface MemberHealthRecordMapper extends BaseMapper<MemberHealthRecord> {

    @Select("SELECT " +
            "mhr.id," +
            "mhr.member," +
            "mhr.disease," +
            "mhr.evaluation," +
            "mhr.remark," +
            "mhr.department," +
            "mhr.created_by createdBy," +
            "mhr.create_time createTime," +
            "mhr.flag,mhr.project " +
            "FROM `member_health_record` mhr " +
            "left join member m on mhr.member = m.id " +
            "where m.clinic = #{depId} ORDER BY mhr.create_time desc")
    List<MemberHealthRecord> selectByThisDept(Integer depId);

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> selectByName(@Param("key") String key);

    List<Map<String,Object>> selectAllByDep(@Param("depId") Integer depId);

    List<Map<String,Object>> selectAllToAdd();


}
