package com.cebrains.hrc.modular.member.service;

import com.cebrains.hrc.common.persistence.model.Member;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface IMemberService extends IService<Member> {
    List<Member> getPhone(String phone);

    List<Member> selectByClinic(Integer clinic);

    List<Map<String,Object>> selectSuggestCardList(String key);

    List<Member> selectByName(String realName);

    List<Member> selectHealthRecordById(Integer id);

    List<Map<String,Object>> selectByClinicHealth(Integer clinic);

    List<Map<String,Object>> selectAllNoBy();
}
