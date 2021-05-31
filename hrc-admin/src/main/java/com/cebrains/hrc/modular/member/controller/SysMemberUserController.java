package com.cebrains.hrc.modular.member.controller;

import com.cebrains.hrc.common.annotion.BussinessLog;
import com.cebrains.hrc.common.annotion.Permission;
import com.cebrains.hrc.common.constant.Const;
import com.cebrains.hrc.common.constant.dictmap.UserDict;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.state.ManagerStatus;
import com.cebrains.hrc.common.exception.BizExceptionEnum;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.common.persistence.model.User;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.base.tips.Tip;
import com.cebrains.hrc.core.exception.HRCException;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.core.util.ToolUtil;
import com.cebrains.hrc.modular.member.service.IMemberUserService;
import com.cebrains.hrc.modular.member.service.impl.MemberUserServiceImpl;
import com.cebrains.hrc.modular.resource.wrapper.MemberRehabilitationRecordWrapper;
import com.cebrains.hrc.modular.system.dao.UserMgrDao;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统端 个人会员信息管理
 */
@Controller
@RequestMapping("sysMemberUser")
public class SysMemberUserController extends BaseController {

    private String PREFIX = "/member/sysMemberUser/";

    @Autowired
    private IMemberUserService memberUserService;

    @Autowired
    private MemberUserServiceImpl service;

    @Resource
    private UserMgrDao managerDao;

    @Resource
    private UserMapper userMapper;

    /**
     * 跳转到会员信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sysMemberUser.html";
    }

    /**
     * 跳转到个人会员信息详情
     */
    @RequestMapping("/sysMemberUser_detail/{sysMemberUserId}")
    public String memberHealthSurveyDetail(@PathVariable Integer sysMemberUserId, Model model) {
        MemberUser memberUser = memberUserService.selectById(sysMemberUserId);
        model.addAttribute("item",memberUser);
        LogObjectHolder.me().set(memberUser);
        return PREFIX + "sysMemberUser_detail.html";
    }

    /**
     * 获取个人信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer ids) {

        return memberUserService.selectList(null);
    }

    /**
     * 删除个人会员
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Integer sysMemberUserId) {
        MemberUser user=memberUserService.selectById(sysMemberUserId);
        Integer userId=user.getMemberUser();
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new HRCException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        memberUserService.deleteById(sysMemberUserId);
        return SUCCESS_TIP;
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.userMapper.selectById(userId);
        Integer deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        } else {
            throw new HRCException(BizExceptionEnum.NO_PERMITION);
        }

    }
}
