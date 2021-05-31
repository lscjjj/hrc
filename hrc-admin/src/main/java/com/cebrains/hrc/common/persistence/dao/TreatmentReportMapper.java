package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.TreatmentReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-10-09
 */
public interface TreatmentReportMapper extends BaseMapper<TreatmentReport> {

    @Select("SELECT tr.*,tr.create_time createTime " +
            "FROM treatment_report tr " +
            "left join member m on m.id=tr.member " +
            "where m.clinic = #{depId} ORDER BY tr.create_time desc")
    List<TreatmentReport> selectThisDept(Integer depId);

}
