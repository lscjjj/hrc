package com.cebrains.hrc.modular.resource.controller;

import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.modular.resource.wrapper.TcmHealthSurveyOptionWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.cebrains.hrc.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.cebrains.hrc.common.persistence.model.TcmHealthSurveyOption;
import com.cebrains.hrc.modular.resource.service.ITcmHealthSurveyOptionService;

import java.util.List;

/**
 * 中医体检报告控制器
 *
 * @author frank
 * @Date 2020-12-25 10:21:00
 */
@Controller
@RequestMapping("/tcmHealthSurveyOption")
public class TcmHealthSurveyOptionController extends BaseController {

    private String PREFIX = "/resource/tcmHealthSurveyOption/";

    @Autowired
    private ITcmHealthSurveyOptionService tcmHealthSurveyOptionService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tcmHealthSurveyOption.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/tcmHealthSurveyOption_add")
    public String tcmHealthSurveyOptionAdd() {
        return PREFIX + "tcmHealthSurveyOption_add.html";
    }

    /**
     * 跳转到表格
     */
    @RequestMapping("/memberHealthSurveryMedicine")
    public String tcmHealthSurveyOptionindex() {
        return "/member/memberHealthSurvey/memberHealthSurvert_medicine.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/tcmHealthSurveyOption_update/{tcmHealthSurveyOptionId}")
    public String tcmHealthSurveyOptionUpdate(@PathVariable Integer tcmHealthSurveyOptionId, Model model) {
        TcmHealthSurveyOption tcmHealthSurveyOption = tcmHealthSurveyOptionService.selectById(tcmHealthSurveyOptionId);
        model.addAttribute("item",tcmHealthSurveyOption);
        LogObjectHolder.me().set(tcmHealthSurveyOption);
        return PREFIX + "tcmHealthSurveyOption_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<TcmHealthSurveyOption> tcmHealthSurveyOption = tcmHealthSurveyOptionService.selectList(null);
        return new TcmHealthSurveyOptionWrapper(tcmHealthSurveyOption).wrap();
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TcmHealthSurveyOption tcmHealthSurveyOption) {
        tcmHealthSurveyOptionService.insert(tcmHealthSurveyOption);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tcmHealthSurveyOptionId) {
        tcmHealthSurveyOptionService.deleteById(tcmHealthSurveyOptionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TcmHealthSurveyOption tcmHealthSurveyOption) {
        tcmHealthSurveyOptionService.updateById(tcmHealthSurveyOption);
        return super.SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{tcmHealthSurveyOptionId}")
    @ResponseBody
    public Object detail(@PathVariable("tcmHealthSurveyOptionId") Integer tcmHealthSurveyOptionId) {
        return tcmHealthSurveyOptionService.selectById(tcmHealthSurveyOptionId);
    }
}
