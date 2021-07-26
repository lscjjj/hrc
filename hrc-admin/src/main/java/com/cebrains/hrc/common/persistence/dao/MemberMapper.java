package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.Member;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface MemberMapper extends BaseMapper<Member> {

    String findMemberNameByTreatmentId(@Param("tid") Integer treatment);

    Member getMemberByCard(@Param("card") Integer card);

    List<Member> getPhone(String phone);

    List<Member> selectByClinic(Integer clinic);

    List<Map<String,Object>> selectSuggestCardList(String key);

    List<Member> selectByName(String realName);

    List<Member> selectHealthRecordById(Integer id);

    List<Map<String,Object>> selectByClinicHealth(Integer clinic);

    List<Map<String,Object>> selectAllNoBy();

}
