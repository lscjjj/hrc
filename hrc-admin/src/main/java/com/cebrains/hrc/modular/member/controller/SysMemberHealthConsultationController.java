package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.*;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IMemberHealthConsultationAttachmentService;
import com.cebrains.hrc.modular.member.service.IMemberHealthConsultationService;
import com.cebrains.hrc.modular.resource.wrapper.MemberHealthConsultationWrapper;
import com.cebrains.hrc.modular.resource.wrapper.MemberRehabilitationRecordWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统端 咨询管理
 */
@Controller
@RequestMapping("sysMemberHealthConsultation")
public class SysMemberHealthConsultationController extends BaseController {

    /**
     * 前缀
     */
    private String PREFIX = "/member/sysMemberHealthConsultation/";

    @Autowired
    private IMemberHealthConsultationService memberHealthConsultationService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMemberHealthConsultationAttachmentService memberHealthConsultationAttachmentService;

    /**
     * 跳转到咨询首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sysMemberHealthConsultation.html";
    }

    /**
     * 跳转到咨询详情
     */
    @RequestMapping("/sysMemberHealthConsultation_detail/{sysMemberHealthConsultationId}")
    public String memberHealthConsultationDetail(@PathVariable Integer sysMemberHealthConsultationId, Model model) {
        MemberHealthConsultation memberHealthConsultation = memberHealthConsultationService.selectById(sysMemberHealthConsultationId);
        memberHealthConsultation.setDepartmentName(ConstantFactory.me().getDeptName(memberHealthConsultation.getDepartment()));
//        memberHealthConsultation.setCreatedByName(ConstantFactory.me().getUserNameById(memberHealthConsultation.getCreatedBy()));
        memberHealthConsultation.setFamilyName(ConstantFactory.me().getFamilyName(memberHealthConsultation.getFamily()));
        memberHealthConsultation.setStateName(ConstantFactory.me().getStateName(memberHealthConsultation.getState()));
        memberHealthConsultation.setAppellationName(ConstantFactory.me().getAppellationName(memberHealthConsultation.getFamily()));
        memberHealthConsultation.setMemberUserPhone(ConstantFactory.me().getMemberUserPhone(memberHealthConsultation.getMemberUser()));
        List<MemberHealthConsultationAttachment> hcas = memberHealthConsultationAttachmentService.selectList(new EntityWrapper<MemberHealthConsultationAttachment>()
                .eq("health_consultation", sysMemberHealthConsultationId));
        if(hcas.size()>0){
            model.addAttribute("document",hcas.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        model.addAttribute("item",memberHealthConsultation);
        LogObjectHolder.me().set(memberHealthConsultation);
        return PREFIX + "sysMemberHealthConsultation_detail.html";
    }

    /**
     * 跳转到修改健康档案
     */
    @RequestMapping("/sysMemberHealthConsultation_update/{sysMemberHealthRecordId}")
    public String memberHealthConsultationUpdate(@PathVariable Integer sysMemberHealthRecordId, Model model) {
        MemberHealthConsultation memberHealthConsultation = memberHealthConsultationService.selectById(sysMemberHealthRecordId);
        memberHealthConsultation.setDepartmentName(ConstantFactory.me().getDeptName(memberHealthConsultation.getDepartment()));
//        memberHealthConsultation.setCreatedByName(ConstantFactory.me().getUserNameById(memberHealthConsultation.getCreatedBy()));
        memberHealthConsultation.setFamilyName(ConstantFactory.me().getFamilyName(memberHealthConsultation.getFamily()));
        memberHealthConsultation.setStateName(ConstantFactory.me().getStateName(memberHealthConsultation.getState()));
        memberHealthConsultation.setAppellationName(ConstantFactory.me().getAppellationName(memberHealthConsultation.getFamily()));
        memberHealthConsultation.setMemberUserPhone(ConstantFactory.me().getMemberUserPhone(memberHealthConsultation.getMemberUser()));
        List<MemberHealthConsultationAttachment> hcas = memberHealthConsultationAttachmentService.selectList(new EntityWrapper<MemberHealthConsultationAttachment>()
                .eq("health_consultation", sysMemberHealthRecordId));
        if(hcas.size()>0){
            model.addAttribute("document",hcas.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        model.addAttribute("item",memberHealthConsultation);
        fillIfCanMaintainForOtherDept(model);
        LogObjectHolder.me().set(memberHealthConsultation);
        return PREFIX + "sysMemberHealthConsultation_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer ids) {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.deptId;

        List<MemberHealthConsultation> memberHealthConsultations = memberHealthConsultationService.selectByThisDept(depId);
        if (shiroUser.getRoleList().contains(1)){ // 1 代表超级管理员角色
            memberHealthConsultations = memberHealthConsultationService.selectList(null);
        }
        return new MemberHealthConsultationWrapper(memberHealthConsultations).wrap();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberHealthConsultation memberHealthConsultation) {
        memberHealthConsultation.setState(2);
        memberHealthConsultationService.updateById(memberHealthConsultation);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除咨询
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer sysMemberHealthConsultationId) {
        memberHealthConsultationService.deleteById(sysMemberHealthConsultationId);
        memberHealthConsultationAttachmentService.delete(new EntityWrapper<MemberHealthConsultationAttachment>()
                .eq("health_consultation",sysMemberHealthConsultationId));
        return SUCCESS_TIP;
    }

    private void fillIfCanMaintainForOtherDept(Model model) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_SHC_EDIT_OTHER_DEPT)){
            ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
            Integer departmentId = shiroUser.getDeptId();
            String departmentName = shiroUser.getDeptName();
            model.addAttribute("departmentId",departmentId);
            model.addAttribute("departmentName",departmentName);
            Wrapper<User> userWrapper = new EntityWrapper<>();
            userWrapper = userWrapper.eq("deptid", departmentId);
            List<User> users = userMapper.selectList(userWrapper);
            model.addAttribute("users",users);
        }else{
            model.addAttribute("departmentId",null);
            model.addAttribute("departmentName",null);
            model.addAttribute("users",new ArrayList<User>());
        }
    }
}
