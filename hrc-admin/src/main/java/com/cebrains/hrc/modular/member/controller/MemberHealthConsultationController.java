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
import java.util.stream.Collectors;

/**
 * 会员端的咨询管理
 */
@Controller
@RequestMapping("memberHealthConsultation")
public class MemberHealthConsultationController extends BaseController {

    /**
     * 前缀
     */
    private String PREFIX = "/member/memberHealthConsultation/";

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
        return PREFIX + "memberHealthConsultation.html";
    }

    /**
     * 跳转到添加咨询
     */
    @RequestMapping("/memberHealthConsultation_add")
    public String memberHealthConsultationAdd(Model model) {
        fillIfCanMaintainForOtherDept(model);
        return PREFIX + "memberHealthConsultation_add.html";
    }

    /**
     * 跳转到咨询详情
     */
    @RequestMapping("/memberHealthConsultation_details")
    public String memberHealthConsultationDetails(Model model) {
//        fillIfCanMaintainForOtherDept(model);
        return PREFIX + "memberHealthConsultation_detail.html";
    }

    /**
     * 咨询详情
     */
    @RequestMapping("/memberHealthConsultation_detail/{memberHealthConsultationId}")
    @ResponseBody
    public Object memberHealthConsultationDetail(@PathVariable Integer memberHealthConsultationId, Model model) {
        MemberHealthConsultation memberHealthConsultation = memberHealthConsultationService.selectById(memberHealthConsultationId);
        memberHealthConsultation.setDepartmentName(ConstantFactory.me().getDeptName(memberHealthConsultation.getDepartment()));
//        memberHealthConsultation.setCreatedByName(ConstantFactory.me().getUserNameById(memberHealthConsultation.getCreatedBy()));
        memberHealthConsultation.setFamilyName(ConstantFactory.me().getFamilyName(memberHealthConsultation.getFamily()));
        memberHealthConsultation.setStateName(ConstantFactory.me().getStateName(memberHealthConsultation.getState()));
        memberHealthConsultation.setAppellationName(ConstantFactory.me().getAppellationName(memberHealthConsultation.getFamily()));
        List<MemberHealthConsultationAttachment> hcas = memberHealthConsultationAttachmentService.selectList(new EntityWrapper<MemberHealthConsultationAttachment>()
                .eq("health_consultation", memberHealthConsultationId));
        if(hcas.size()>0){
            model.addAttribute("document",hcas.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        model.addAttribute("item",memberHealthConsultation);
        LogObjectHolder.me().set(memberHealthConsultation);
        return memberHealthConsultation;
    }

    /**
     * 获取健用户咨询
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id= user.id;
        List<MemberHealthConsultation> memberHealthConsultations
                = memberHealthConsultationService.selectList(new EntityWrapper<MemberHealthConsultation>()
                    .eq("member_user",id));
        return new MemberHealthConsultationWrapper(memberHealthConsultations).wrap();
    }

    /**
     * 新增咨询
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberHealthConsultation memberHealthConsultation, @RequestParam(value="attachment[]",required=false)List<String> attachment) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        memberHealthConsultation.setMemberUser(user.id);
        memberHealthConsultationService.insert(memberHealthConsultation);
        // 附件
        if(attachment!=null){
            attachment.forEach(a ->{
                MemberHealthConsultationAttachment mhca = new MemberHealthConsultationAttachment();
                mhca.setHealthConsultation(memberHealthConsultation.getId());
                mhca.setPath(a);
                memberHealthConsultationAttachmentService.insert(mhca);
            });
        }else{

        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除咨询
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberHealthConsultationId) {
        memberHealthConsultationService.deleteById(memberHealthConsultationId);
        memberHealthConsultationAttachmentService.delete(new EntityWrapper<MemberHealthConsultationAttachment>()
                .eq("health_consultation",memberHealthConsultationId));
        return SUCCESS_TIP;
    }


    private void fillIfCanMaintainForOtherDept(Model model) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_HC_EDIT_OTHER_DEPT)){
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
