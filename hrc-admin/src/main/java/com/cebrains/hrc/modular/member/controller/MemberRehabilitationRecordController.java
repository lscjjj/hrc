package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;
import com.cebrains.hrc.common.persistence.model.User;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IMemberRehabilitationRecordService;
import com.cebrains.hrc.modular.member.service.impl.MemberUserServiceImpl;
import com.cebrains.hrc.modular.resource.wrapper.MemberRehabilitationRecordWrapper;
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
 * 会员端 康复记录 控制器
 */
@Controller
@RequestMapping("memberRehabilitationRecord")
public class MemberRehabilitationRecordController extends BaseController {

    /**
     * 前缀
     */
    private static String PREFIX = "/member/memberRehabilitationRecord/";

    @Autowired
    private IMemberRehabilitationRecordService rehabilitationRecordService;

    @Autowired
    private MemberUserServiceImpl memberUserService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 跳转到康复记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "memberRehabilitationRecord.html";
    }

    /**
     * 跳转到添加康复记录
     */
    @RequestMapping("/memberRehabilitationRecord_add")
    public String memberHealthSurveyAdd(Model model) {
        fillIfCanMaintainForOtherDept(model);
        return PREFIX + "memberRehabilitationRecord_add.html";
    }

    /**
     * 跳转到修改健康档案
     */
    @RequestMapping("/memberRehabilitationRecord_update/{memberRehabilitationRecordId}")
    public String memberHealthSurveyUpdate(@PathVariable Integer memberRehabilitationRecordId, Model model) {

        MemberRehabilitationRecord rehabilitationRecord = rehabilitationRecordService.selectById(memberRehabilitationRecordId);
        rehabilitationRecord.setDepartmentName(ConstantFactory.me().getDeptName(rehabilitationRecord.getDepartment()));
        model.addAttribute("item",rehabilitationRecord);

        LogObjectHolder.me().set(rehabilitationRecord);

        return PREFIX + "memberRehabilitationRecord_edit.html";
    }

    /**
     * 新增康复记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberRehabilitationRecord rehabilitationRecord) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        rehabilitationRecord.setMemberUser(user.id);
        rehabilitationRecordService.insert(rehabilitationRecord);
        return super.SUCCESS_TIP;
    }

    /**
     * 修改康复记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberRehabilitationRecord rehabilitationRecord) {
        rehabilitationRecordService.updateById(rehabilitationRecord);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberRehabilitationRecordId) {
        rehabilitationRecordService.deleteById(memberRehabilitationRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 获取康复记录
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id= user.id;
        List<MemberRehabilitationRecord> rehabilitationRecords
                = rehabilitationRecordService.selectList(new EntityWrapper<MemberRehabilitationRecord>()
                    .eq("member_user",id) );
        return new MemberRehabilitationRecordWrapper(rehabilitationRecords).wrap();
    }

    private void fillIfCanMaintainForOtherDept(Model model) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_HS_EDIT_OTHER_DEPT)){
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
