package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.*;
import com.cebrains.hrc.common.persistence.vo.MemberHealthSurveyVo;
import com.cebrains.hrc.common.persistence.vo.RProjectVO;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IMemberHealthSurveyService;
import com.cebrains.hrc.modular.resource.service.IHealthSurveyOptionService;
import com.cebrains.hrc.modular.resource.service.impl.HealthSurveyOptionImpl;
import com.cebrains.hrc.modular.resource.wrapper.HealthSurveyOptionWrapper;
import com.cebrains.hrc.modular.resource.wrapper.MemberHealthSurveyWrapper;
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
 * 健康信息管理 控制器
 */
@Controller
@RequestMapping("memberHealthSurvey")
public class MemberHealthSurveyController extends BaseController {

    /**
     * 前缀
     */
    private String PREFIX = "/member/memberHealthSurvey/";

    @Autowired
    private IMemberHealthSurveyService memberHealthSurveyService;

    @Autowired
    private HealthSurveyOptionImpl healthSurveyOption;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IHealthSurveyOptionService healthSurveyOptionService;

    /**
     * 跳转到会员健康状况首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "memberHealthSurvey.html";
    }

    /**
     * 跳转到添加会员健康状况调查
     */
    @RequestMapping("/memberHealthSurvey_add")
    public String memberHealthSurveyAdd(Model model) {
        fillIfCanMaintainForOtherDept(model);
        return PREFIX + "memberHealthSurvey_add.html";
    }

    /**
     * 跳转到修改健康档案
     */
    @RequestMapping("/memberHealthSurvey_option/{memberHealthSurveyId}")
    public String memberHealthSurveyUpdate(@PathVariable Integer memberHealthSurveyId, Model model) {
        MemberHealthSurvey memberHealthSurvey = memberHealthSurveyService.selectById(memberHealthSurveyId);
        model.addAttribute("item",memberHealthSurvey);
        fillIfCanMaintainForOtherDept(model);
        LogObjectHolder.me().set(memberHealthSurvey);
        return PREFIX + "memberHealthSurvey_edit.html";
    }

    @RequestMapping("/memberHealthSurvey_option")
    public String memberHealthSurveyUpdate(Model model) {

        return PREFIX + "memberHealthSurvey_edit.html";
    }

    /**
     * 跳转到会员健康调查详情
     */
    @RequestMapping("/memberHealthSurvey_detail/{memberHealthSurveyId}")
    public String memberHealthSurveyDetail(@PathVariable Integer memberHealthSurveyId, Model model) {
        MemberHealthSurvey memberHealthSurvey = memberHealthSurveyService.selectById(memberHealthSurveyId);
        memberHealthSurvey.setDepartmentName(ConstantFactory.me().getDeptName(memberHealthSurvey.getDepartment()));
        memberHealthSurvey.setCreatedByName(ConstantFactory.me().getUserNameById(memberHealthSurvey.getCreatedBy()));
        memberHealthSurvey.setMemberName(ConstantFactory.me().getMemberName(memberHealthSurvey.getMember()));
        memberHealthSurvey.setMemberPhone(ConstantFactory.me().getMemberPhone(memberHealthSurvey.getMember()));
        memberHealthSurvey.setMemberSex(ConstantFactory.me().getMemberSex(memberHealthSurvey.getMember()));
        memberHealthSurvey.setMemberAddress(ConstantFactory.me().getMemberAddress(memberHealthSurvey.getMember()));
        model.addAttribute("item",memberHealthSurvey);
        LogObjectHolder.me().set(memberHealthSurvey);
        return PREFIX + "memberHealthSurvey_detail.html";
    }


    /**
     * 选择调查内容选项
     */
    @RequestMapping("/options")
    @ResponseBody
    public Object solutions( String condition) {
        List<HealthSurveyOption> projects = healthSurveyOptionService.selectList(null);
        return new HealthSurveyOptionWrapper(projects).wrap();
    }

    /**
     * 获取会员健康状况列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.deptId;

        List<MemberHealthSurvey> memberHealthSurveys = memberHealthSurveyService.selectByThisDept(depId);
        if (shiroUser.roleList.contains(1)){ // 1 代表超级管理员角色
            memberHealthSurveys = memberHealthSurveyService.selectList(null);
        }
        return new MemberHealthSurveyWrapper(memberHealthSurveys).wrap();
    }

    /**
     * 新增会员健康状况
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberHealthSurvey memberHealthSurvey) {
        memberHealthSurveyService.insert(memberHealthSurvey);

        return super.SUCCESS_TIP;
    }

    /**
     * 删除会员健康状况
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberHealthSurveyId) {
        memberHealthSurveyService.deleteById(memberHealthSurveyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员健康状况
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberHealthSurvey memberHealthSurvey) {
        memberHealthSurveyService.updateById(memberHealthSurvey);
        return super.SUCCESS_TIP;
    }

    /**
     * 会员健康状况详情
     */
    @RequestMapping(value = "/detail/{memberHealthSurveyId}")
    @ResponseBody
    public Object detail(@PathVariable("memberHealthSurveyId") Integer memberHealthSurveyId) {
        return memberHealthSurveyService.selectById(memberHealthSurveyId);
    }

    private void fillIfCanMaintainForOtherDept(Model model) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_RR_EDIT_OTHER_DEPT)){
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
