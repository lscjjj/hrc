package com.cebrains.hrc.modular.station.controller;

import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.*;
import com.cebrains.hrc.common.persistence.vo.RProjectVO;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.healthProgram.service.IHealthProgramCategoryService;
import com.cebrains.hrc.modular.healthProgram.service.IHealthProgramService;
import com.cebrains.hrc.modular.member.service.IMemberHealthRecordAttachmentService;
import com.cebrains.hrc.modular.member.service.IMemberHealthRecordService;
import com.cebrains.hrc.modular.resource.service.IProjectService;
import com.cebrains.hrc.modular.resource.wrapper.RehabilitationProgramWrapper;
import com.cebrains.hrc.modular.station.service.IRehabilitationProgramAttachmentService;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.modular.station.service.IRehabilitationProgramService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 康复方案控制器
 *
 * @author frank
 * @Date 2018-07-04 22:05:17
 */
@Controller
@RequestMapping("/rehabilitationProgram")
public class RehabilitationProgramController extends BaseController {

    private String PREFIX = "/station/rehabilitationProgram/";

    @Autowired
    private IRehabilitationProgramService rehabilitationProgramService;
    @Autowired
    private IMemberHealthRecordService memberHealthRecordService;
    @Autowired
    private IMemberHealthRecordAttachmentService memberHealthRecordAttachmentService;
    @Autowired
    private IHealthProgramCategoryService healthProgramCategoryService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IHealthProgramService healthProgramService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IRehabilitationProgramAttachmentService rehabilitationProgramAttachmentService;

    /**
     * 跳转到康复方案首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "rehabilitationProgram.html";
    }

    /**
     * 跳转到添加康复方案
     */
    @RequestMapping("/rehabilitationProgram_add")
    public String rehabilitationProgramAdd(Model model) {
        fillIfCanMaintainForOtherDept(model);
        List<HealthProgramCategory> healthProgramCategories = healthProgramCategoryService.selectList(null);
        List<Dict> allProjectCategories = ConstantFactory.me().findAllProjectCategories();
        List<Dict> finalEffectDict = ConstantFactory.me().findFinalEffectDict();
        List<Dict> customerSatisfactionDict = ConstantFactory.me().findCustomerSatisfactionDict();
        List<RProjectVO> rps = new ArrayList<>();
        for (Dict d : allProjectCategories) {
            RProjectVO rp = new RProjectVO();
            rp.setName(d.getName());
            Wrapper<Project> projectWrapper = new EntityWrapper<>();
            projectWrapper.eq("category", d.getNum());
            List<Project> projects = projectService.selectList(projectWrapper);
            rp.setRps(projects);
            rps.add(rp);
        }
        model.addAttribute("rps", rps);
        model.addAttribute("finalEffectDict", finalEffectDict);
        model.addAttribute("customerSatisfactionDict", customerSatisfactionDict);
        return PREFIX + "rehabilitationProgram_add.html";
    }

    /**
     * 根据分类获取项目
     */
    @PostMapping("/projectsByCategory")
    @ResponseBody
    public List<Project> projectsByCategory(Integer pc) {
        Wrapper<Project> projectWrapper = new EntityWrapper<>();
        projectWrapper.eq("category", pc);
        List<Project> projects = projectService.selectList(projectWrapper);
        return projects;
    }

    /**
     * 跳转到修改康复方案
     */
    @RequestMapping("/rehabilitationProgram_update/{rehabilitationProgramId}")
    public String rehabilitationProgramUpdate(@PathVariable Integer rehabilitationProgramId, @RequestParam(value = "hrid", required = false) Integer hrid, Model model) {
        RehabilitationProgram rehabilitationProgram = null;
        if (rehabilitationProgramId == 0) {
            ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
            Integer createdBy = shiroUser.getId();
            Integer departmentId = shiroUser.getDeptId();
            rehabilitationProgram = rehabilitationProgramService.selectOne(new EntityWrapper<RehabilitationProgram>().eq("member_health_record", hrid));
            if (rehabilitationProgram == null || rehabilitationProgram.getId() == null || rehabilitationProgram.getId() <= 0) {
                rehabilitationProgram = new RehabilitationProgram();
                MemberHealthRecord memberHealthRecord = memberHealthRecordService.selectById(hrid);
                rehabilitationProgram.setMemberHealthRecord(hrid);
                rehabilitationProgram.setMember(memberHealthRecord.getMember());
                rehabilitationProgram.setCreatedBy(createdBy);
                rehabilitationProgram.setDepartment(departmentId);
                rehabilitationProgram.setDisease(memberHealthRecord.getEvaluation());
                rehabilitationProgram.setSurveyInfo(memberHealthRecord.getSurveyInfo());
                rehabilitationProgram.setTcmSurveyInfo(memberHealthRecord.getTcmSurveyInfo());
                rehabilitationProgramService.insert(rehabilitationProgram);

                List<MemberHealthRecordAttachment> memberHealthRecordAttachments = memberHealthRecordAttachmentService.selectList(new EntityWrapper<MemberHealthRecordAttachment>().eq("health_record", hrid));
                Integer rpId = rehabilitationProgram.getId();
                rehabilitationProgramId = rpId;

                if (memberHealthRecordAttachments != null) {
                    memberHealthRecordAttachments.forEach(a -> {
                        RehabilitationProgramAttachment mhra = new RehabilitationProgramAttachment();
                        mhra.setRehabilitationProgram(rpId);
                        mhra.setPath(a.getPath());
                        mhra.setDistinction(a.getDistinction());
                        rehabilitationProgramAttachmentService.insert(mhra);
                    });
                }
            }
        } else {
            rehabilitationProgram = rehabilitationProgramService.selectById(rehabilitationProgramId);
        }
        rehabilitationProgram.setMemberName(ConstantFactory.me().getMemberName(rehabilitationProgram.getMember()));
        model.addAttribute("item", rehabilitationProgram);
        List<HealthProgramCategory> healthProgramCategories = healthProgramCategoryService.selectList(null);
        model.addAttribute("categories", healthProgramCategories);
        fillIfCanMaintainForOtherDept(model);
        List<RehabilitationProgramAttachment> hras = rehabilitationProgramAttachmentService.selectList(new EntityWrapper<RehabilitationProgramAttachment>()
                .eq("rehabilitation_program", rehabilitationProgram.getId())
                .eq("distinction", 1));

        if (hras.size() > 0) {
            model.addAttribute("document", hras.stream().map(h -> h.getPath()).collect(Collectors.joining(",")));
        } else {
            model.addAttribute("document", "");
        }

        List<RehabilitationProgramAttachment> faceImgAttachment = rehabilitationProgramAttachmentService.selectList(new EntityWrapper<RehabilitationProgramAttachment>()
                .eq("rehabilitation_program", rehabilitationProgram.getId())
                .eq("distinction", 3));
        if (faceImgAttachment.size() > 0) {
            model.addAttribute("faceImgAttachment", faceImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.joining(",")));
        } else {
            model.addAttribute("faceImgAttachment", "");
        }

        List<RehabilitationProgramAttachment> tongueImgAttachment = rehabilitationProgramAttachmentService.selectList(new EntityWrapper<RehabilitationProgramAttachment>()
                .eq("rehabilitation_program", rehabilitationProgram.getId())
                .eq("distinction", 4));
        if (tongueImgAttachment.size() > 0) {
            model.addAttribute("tongueImgAttachment", tongueImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.joining(",")));
        } else {
            model.addAttribute("tongueImgAttachment", "");
        }

        List<RehabilitationProgramAttachment> partImgAttachment = rehabilitationProgramAttachmentService.selectList(new EntityWrapper<RehabilitationProgramAttachment>()
                .eq("rehabilitation_program", rehabilitationProgram.getId())
                .eq("distinction", 5));
        if (partImgAttachment.size() > 0) {
            model.addAttribute("partImgAttachment", partImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.joining(",")));
        } else {
            model.addAttribute("partImgAttachment", "");
        }


        List<Dict> finalEffectDict = ConstantFactory.me().findFinalEffectDict();
        List<Dict> customerSatisfactionDict = ConstantFactory.me().findCustomerSatisfactionDict();
        model.addAttribute("finalEffectDict", finalEffectDict);
        model.addAttribute("customerSatisfactionDict", customerSatisfactionDict);
        if (rehabilitationProgram.getFinalEffect() != null && rehabilitationProgram.getFinalEffect() != 0){
            model.addAttribute("finalEffect",ConstantFactory.me().getFinalEffectName(rehabilitationProgram.getFinalEffect()));
        }else {
            model.addAttribute("finalEffect","");
        }

        if (rehabilitationProgram.getCustomerSatisfaction() != null && rehabilitationProgram.getCustomerSatisfaction() >0){
            model.addAttribute("customerSatisfaction", ConstantFactory.me().getCustomerSatisfactionName(rehabilitationProgram.getCustomerSatisfaction()));
        }else{
            model.addAttribute("customerSatisfaction","");
        }

        LogObjectHolder.me().set(rehabilitationProgram);
        return PREFIX + "rehabilitationProgram_edit.html";
    }


    /**
     * 获取康复方案列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.getDeptId();

        List<RehabilitationProgram> rehabilitationPrograms = rehabilitationProgramService.selectByThisDept(depId);

        if (shiroUser.getRoleList().contains(1)) { // 1 代表超级管理员角色
            rehabilitationPrograms = rehabilitationProgramService.selectList(new EntityWrapper<RehabilitationProgram>()
                    .orderDesc(Collections.singletonList("create_time")));
        }
        return new RehabilitationProgramWrapper(rehabilitationPrograms).wrap();
    }

    /**
     * 搜索框提示
     */
    @RequestMapping(value = {"/search/{k}", "/search"})
    @ResponseBody
    public Object search(@PathVariable(value = "k", required = false) String k) {
        List<Map<String, Object>> result = null;
//        List<MembershipCard> membershipCardList = null;
        result = rehabilitationProgramService.selectAll();
        if (k != null) {
            result = rehabilitationProgramService.selectByName(k);
        }

        return result;
    }

    /**
     * 健康康复添加
     *
     * @return
     */
    @RequestMapping(value = "/toAdd")
    @ResponseBody
    public Object toAdd() {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.deptId;

        List<Map<String, Object>> result = null;
        result = memberHealthRecordService.selectAllByDep(depId);
        if (shiroUser.getRoleList().contains(1)) { // 1 代表超级管理员角色
            result = memberHealthRecordService.selectAllToAdd();
        }

        return result;
    }

    /**
     * 新增康复方案
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(RehabilitationProgram rehabilitationProgram, @RequestParam("attachment[]") List<String> attachment) {
        rehabilitationProgramService.insert(rehabilitationProgram);
        // 附件
        if (attachment != null) {
            attachment.forEach(a -> {
                RehabilitationProgramAttachment mhra = new RehabilitationProgramAttachment();
                mhra.setRehabilitationProgram(rehabilitationProgram.getId());
                mhra.setPath(a);
                rehabilitationProgramAttachmentService.insert(mhra);
            });
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除康复方案
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer rehabilitationProgramId) {
        rehabilitationProgramService.deleteById(rehabilitationProgramId);
        return SUCCESS_TIP;
    }

    /**
     * 修改康复方案
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(RehabilitationProgram rehabilitationProgram) {
        rehabilitationProgramService.updateById(rehabilitationProgram);
        return super.SUCCESS_TIP;
    }

    /**
     * 康复方案详情
     */
    @RequestMapping(value = "/detail/{rehabilitationProgramId}")
    @ResponseBody
    public Object detail(@PathVariable("rehabilitationProgramId") Integer rehabilitationProgramId) {

        RehabilitationProgram rehabilitationPrograms = rehabilitationProgramService.selectById(rehabilitationProgramId);

        rehabilitationPrograms.setMemberName(ConstantFactory.me().getMemberName(rehabilitationPrograms.getMember()));
        rehabilitationPrograms.setCreatedByName(ConstantFactory.me().getUserNameById(rehabilitationPrograms.getCreatedBy()));
        rehabilitationPrograms.setDepartmentName(ConstantFactory.me().getDeptName(rehabilitationPrograms.getDepartment()));

        return rehabilitationPrograms;
    }

    private void fillIfCanMaintainForOtherDept(Model model) {
        if (ShiroKit.lacksPermission(IConstantFactory.PERMISSION_RP_EDIT_OTHER_DEPT)) {
            ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
            Integer departmentId = shiroUser.getDeptId();
            String departmentName = shiroUser.getDeptName();
            model.addAttribute("departmentId", departmentId);
            model.addAttribute("departmentName", departmentName);
            Wrapper<User> userWrapper = new EntityWrapper<>();
            userWrapper = userWrapper.eq("deptid", departmentId);
            List<User> users = userMapper.selectList(userWrapper);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("departmentId", null);
            model.addAttribute("departmentName", null);
            model.addAttribute("users", new ArrayList<User>());
        }
    }
}
