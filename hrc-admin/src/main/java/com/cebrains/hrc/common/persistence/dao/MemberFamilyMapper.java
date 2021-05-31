package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberFamily;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 家庭成员 mapper 接口
 */
public interface MemberFamilyMapper extends BaseMapper<MemberFamily> {

    @Select("select * from member_family where member_user=#{id}")
    List<MemberFamily> list(Integer id);
}
