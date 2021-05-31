package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 个人会员mapper 接口
 */
public interface MemberUserMapper extends BaseMapper<MemberUser> {

    @Select("select * from member_user where member_user=#{id}")
    List<MemberUser> list(Integer id);
}
