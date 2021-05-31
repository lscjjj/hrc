package com.cebrains.hrc.modular.member.controller;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.modular.member.service.IMemberRehabilitationRecordService;
import com.cebrains.hrc.modular.resource.wrapper.MemberRehabilitationRecordWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统端 康复记录管理
 */
@Controller
@RequestMapping("sysMemberRehabilitationRecord")
public class SysMemberRehabilitationRecordController extends BaseController {

    /**
     * 前缀
     */
    /**
     * 前缀
     */
    private static String PREFIX = "/member/sysMemberRehabilitationRecord/";

    @Autowired
    private IMemberRehabilitationRecordService rehabilitationRecordService;

    /**
     * 跳转到康复记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sysMemberRehabilitationRecord.html";
    }

    /**
     * 跳转到详情
     */
    @RequestMapping("/sysMemberRehabilitationRecord_detail/{sysMemberRehabilitationRecordId}")
    public String memberHealthSurveyDetail(@PathVariable Integer sysMemberRehabilitationRecordId, Model model) {
        MemberRehabilitationRecord rehabilitationRecord = rehabilitationRecordService.selectById(sysMemberRehabilitationRecordId);
        rehabilitationRecord.setDepartmentName(ConstantFactory.me().getDeptName(rehabilitationRecord.getDepartment()));
        model.addAttribute("item",rehabilitationRecord);
        LogObjectHolder.me().set(rehabilitationRecord);
        return PREFIX + "sysMemberRehabilitationRecord_detail.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer ids) {
        List<MemberRehabilitationRecord> rehabilitationRecords = rehabilitationRecordService.selectList(null);
        return new MemberRehabilitationRecordWrapper(rehabilitationRecords).wrap();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer sysMemberRehabilitationRecordId) {
        rehabilitationRecordService.deleteById(sysMemberRehabilitationRecordId);
        return SUCCESS_TIP;
    }


}
