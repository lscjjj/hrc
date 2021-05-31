package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.Treatment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.TreatmentReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 康护记录 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-05-11
 */
public interface TreatmentMapper extends BaseMapper<Treatment> {

    List<Map> findTreatmentSuggest(@Param("k") String keyword);

    Map findProjectNamesByTreatment(@Param("treatment") Integer treatment);

    List<Map> treatmentByMember(@Param("member")Integer member);

    @Select("SELECT *,t.user_id userId,t.create_time createTime\n" +
            "FROM treatment t\n" +
            "  LEFT JOIN member m ON m.id = t.user_id\n" +
            "WHERE t.id IS NOT NULL and m.clinic = #{departmentId} and t.`status`in (2,3)\n" +
            "ORDER BY t.create_time DESC")
    List<Treatment> selectThisDept(Integer departmentId);
}
