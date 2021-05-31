package com.cebrains.hrc.common.persistence.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 康复记录 mapper 接口
 */
public interface MemberRehabilitationRecordMapper extends BaseMapper<MemberRehabilitationRecord> {

    @Select("select * from member_rehabilitation_record where member_user=#{member_user}")
    List<MemberRehabilitationRecord> list(Integer member_user);
}
