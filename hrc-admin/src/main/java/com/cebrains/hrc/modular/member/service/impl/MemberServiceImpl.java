package com.cebrains.hrc.modular.member.service.impl;

import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.common.persistence.dao.MemberMapper;
import com.cebrains.hrc.modular.member.service.IMemberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> getPhone(String phone) {
        return memberMapper.getPhone(phone);
    }

    @Override
    public List<Member> selectByClinic(Integer clinic) {
        return memberMapper.selectByClinic(clinic);
    }

    @Override
    public List<Map<String, Object>> selectSuggestCardList(String key) {
        return memberMapper.selectSuggestCardList(key);
    }

    @Override
    public List<Member> selectByName(String realName) {
        return memberMapper.selectByName(realName);
    }

    @Override
    public List<Member> selectHealthRecordById(Integer id) {
        return memberMapper.selectHealthRecordById(id);
    }

    @Override
    public List<Map<String, Object>> selectByClinicHealth(Integer clinic) {
        return memberMapper.selectByClinicHealth(clinic);
    }

    @Override
    public List<Map<String, Object>> selectAllNoBy() {
        return memberMapper.selectAllNoBy();
    }
}
