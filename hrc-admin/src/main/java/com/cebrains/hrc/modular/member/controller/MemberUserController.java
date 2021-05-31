package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.common.persistence.vo.MemberUserSuggestVo;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IMemberService;
import com.cebrains.hrc.modular.member.service.IMemberUserService;
import com.cebrains.hrc.modular.member.service.impl.MemberUserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员端   会员基本信息管理
 */
@Controller
@RequestMapping("memberUser")
public class MemberUserController extends BaseController {

    private String PREFIX = "/member/memberUser/";

    @Autowired
    private IMemberUserService memberUserService;

    @Autowired
    private IMemberService memberService;


    @Autowired
    private MemberUserServiceImpl service;

    /**
     * 跳转到会员信息首页
     */
//    @RequestMapping("")
//    public String index() {
//        return PREFIX + "memberUser.html";
//    }


    /**
     * 跳转到修改信息
     */
    @RequestMapping("")
    public String index( Model model) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id=user.id;
        List<MemberUser> list=service.list(id);
        MemberUser memberUser = memberUserService.selectById(list.get(0));
        model.addAttribute("item",memberUser);
//        fillIfCanMaintainForOtherDept(model);
        LogObjectHolder.me().set(memberUser);
        return PREFIX + "memberUser_edit.html";
    }

    /**
     * 获取健康档案列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer ids) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id=user.id;
//        Wrapper<MemberUser> wrapper = new QueryWrapper();
//        List<MemberUser> memberUsers = (List<MemberUser>) memberUserService.selectById(id);
        return service.list(id);
    }

    /**
     * 修改会员健康状况
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberUser memberUser) {
        memberUserService.updateById(memberUser);
        return super.SUCCESS_TIP;
    }

    /**
     * 获取个人会员信息
     */
    @RequestMapping(value = "/infoById")
    @ResponseBody
    public Object infoById(@RequestParam("id")Integer id) {
            MemberUser memberUser = memberUserService.selectById(id);
            return memberUser;
    }

    /**
     * 获取个人会员列表
     */
    @RequestMapping(value = "/suggestList/{k}")
    @ResponseBody
    public Object suggestList(@PathVariable String k) {

        List<MemberUserSuggestVo> result = new ArrayList<>();

        Wrapper<MemberUser> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("name", "%"+k+"%");

        Wrapper<Member> wrapper2 = new EntityWrapper<>();
        wrapper2 = wrapper2.like("real_name", "%"+k+"%");

        List<MemberUser> memberUsers = memberUserService.selectList(wrapper);
        List<Member> members = memberService.selectList(wrapper2);
        if(memberUsers!=null && members != null){
            memberUsers.forEach(m -> result.add(new MemberUserSuggestVo(m.getId(),m.getName(),m.getPhone())));
            members.forEach(m -> result.add(new MemberUserSuggestVo(m.getId(),m.getRealName(),m.getPhone())));
            return result;
        }else if (members == null){
            memberUsers.forEach(m -> result.add(new MemberUserSuggestVo(m.getId(),m.getName(),m.getPhone())));
            return result;
        }else {
            members.forEach(m -> result.add(new MemberUserSuggestVo(m.getId(),m.getRealName(),m.getPhone())));
            return result;
        }
    }
}
