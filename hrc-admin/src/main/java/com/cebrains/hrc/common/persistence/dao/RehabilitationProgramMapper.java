package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.MemberHealthRecord;
import com.cebrains.hrc.common.persistence.model.RehabilitationProgram;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 康复方案 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-07-04
 */
public interface RehabilitationProgramMapper extends BaseMapper<RehabilitationProgram> {

    @Select("SELECT \n" +
            "rp.id,\n" +
            "rp.member,\n" +
            "rp.disease,\n" +
            "rp.department,\n" +
            "rp.description,\n" +
            "rp.curative_effect curativeEffect,\n" +
            "rp.customer_satisfaction customerSatisfaction,\n" +
            "rp.remark,\n" +
            "rp.create_time createTime,\n" +
            "rp.created_by createdBy,\n" +
            "rp.flag,\n" +
            "rp.member_health_record memberHealthRecord,\n" +
            "rp.final_effect finalEffect\n" +
            "FROM rehabilitation_program rp LEFT JOIN member m on rp.member = m.id WHERE m.clinic = #{depId} ORDER BY rp.create_time desc")
    List<RehabilitationProgram> selectByThisDept(Integer depId);

    List<Map<String,Object>> selectAll();

    List<Map<String,Object>> selectByName(@Param("key") String key);
}
