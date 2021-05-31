package com.cebrains.hrc.modular.resource.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.HealthSurveyOption;
import com.cebrains.hrc.common.persistence.model.User;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.resource.service.IHealthSurveyOptionService;
import com.cebrains.hrc.modular.resource.wrapper.HealthSurveyOptionWrapper;
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
 * 会员健康状况调查表控制器
 */
@Controller
@RequestMapping("/healthSurveyOption")
public class HealthSurveyOptionController extends BaseController {
    /**
     *最后一次测试
     */
    /**
     * 前缀
     */
    private String PREFIX = "/resource/healthSurveyOption/";

    @Autowired
    private IHealthSurveyOptionService surveyOptionService;

    /**
     * 跳转到会员健康调查表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "healthSurveyOption.html";
    }

    /**
     * 跳转到添加调查内容
     */
    @RequestMapping("/healthSurveyOption_add")
    public String projectAdd(Model model) {
        model.addAttribute("projectCategories", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_OPTION));
        return PREFIX + "healthSurveyOption_add.html";
    }

    /**
     * 跳转到修改调查内容
     */
    @RequestMapping("/healthSurveyOption_update/{optionId}")
    public String projectUpdate(@PathVariable Integer optionId, Model model) {
        HealthSurveyOption surveyOption = surveyOptionService.selectById(optionId);
        model.addAttribute("item",surveyOption);
        model.addAttribute("categoryName", ConstantFactory.me().getProjectCategory(surveyOption.getCategory()));
        model.addAttribute("optionCategories",ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_OPTION));
        LogObjectHolder.me().set(surveyOption);
        return PREFIX + "healthSurveyOption_edit.html";
    }

    /**
     * 获取调查表内容
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<HealthSurveyOption> projects = surveyOptionService.selectList(null);
        return new HealthSurveyOptionWrapper(projects).wrap();
    }

    /**
     * 新增调查内容
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HealthSurveyOption surveyOption) {
        surveyOptionService.insert(surveyOption);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除调查内容
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer optionId) {
        surveyOptionService.deleteById(optionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改调查内容
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HealthSurveyOption surveyOption) {
        surveyOptionService.updateById(surveyOption);
        return super.SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{optionId}")
    @ResponseBody
    public Object detail(@PathVariable("optionId") Integer optionId) {
        return surveyOptionService.selectById(optionId);
    }

}
