package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.*;
import com.cebrains.hrc.common.persistence.vo.RProjectVO;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.base.tips.ErrorTip;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.*;
import com.cebrains.hrc.modular.member.service.impl.MemberHealthRecordAttachmentServiceImpl;
import com.cebrains.hrc.modular.resource.service.IProjectService;
import com.cebrains.hrc.modular.resource.wrapper.MemberHealthRecordWrapper;
import com.cebrains.hrc.modular.resource.wrapper.ProjectSecondWrapper;
import com.cebrains.hrc.modular.resource.wrapper.ProjectSupWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.cebrains.hrc.core.log.LogObjectHolder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 健康档案控制器
 *
 * @author frank
 * @Date 2018-07-02 15:58:52
 */
@Controller
@RequestMapping("/memberHealthRecord")
public class MemberHealthRecordController extends BaseController {

    private String PREFIX = "/member/memberHealthRecord/";

    @Autowired
    private IMemberHealthRecordService memberHealthRecordService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IMemberHealthRecordAttachmentService memberHealthRecordAttachmentService;
    @Autowired
    private IProjectService projectService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IProjectSupService iProjectSupService;

    @Autowired
    private IProjectSecondService iProjectSecondService;

    /**
     * 跳转到健康档案首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "memberHealthRecord.html";
    }

    @RequestMapping("/jump")
    public String jumpIndex() {
        return PREFIX + "memberHealthRecord.html";
    }

    /**
     * 跳转到添加健康档案
     */
    @RequestMapping("/memberHealthRecord_add")
    public String memberHealthRecordAdd(Model model) {
        fillIfCanMaintainForOtherDept(model);
        return PREFIX + "memberHealthRecord_add.html";
    }

    /**
     * 跳转到修改健康档案
     */
    @RequestMapping("/memberHealthRecord_update/{memberHealthRecordId}")
    public String memberHealthRecordUpdate(@PathVariable Integer memberHealthRecordId, Model model) {
        MemberHealthRecord memberHealthRecord = memberHealthRecordService.selectById(memberHealthRecordId);
        model.addAttribute("item",memberHealthRecord);
        fillIfCanMaintainForOtherDept(model);
        LogObjectHolder.me().set(memberHealthRecord);
        return PREFIX + "memberHealthRecord_edit.html";
    }

    /**
     * 跳转到修改健康档案
     */
    @RequestMapping("/memberHealthRecord_detail/{memberHealthRecordId}")
    public String memberHealthRecordDetail(@PathVariable Integer memberHealthRecordId, Model model) {
        MemberHealthRecord memberHealthRecord = memberHealthRecordService.selectById(memberHealthRecordId);
        memberHealthRecord.setDepartmentName(ConstantFactory.me().getDeptName(memberHealthRecord.getDepartment()));
        memberHealthRecord.setCreatedByName(ConstantFactory.me().getUserNameById(memberHealthRecord.getCreatedBy()));
        memberHealthRecord.setMemberName(ConstantFactory.me().getMemberName(memberHealthRecord.getMember()));
        memberHealthRecord.setMemberPhone(ConstantFactory.me().getMemberPhone(memberHealthRecord.getMember()));
        //distinction字段 1代表西医体检报告附件 2代表   3代表上传的脸部照片，4舌部照片，5局部照片
        List<MemberHealthRecordAttachment> hras = memberHealthRecordAttachmentService.selectList(new EntityWrapper<MemberHealthRecordAttachment>().eq("health_record", memberHealthRecordId).eq("distinction",1));
        List<MemberHealthRecordAttachment> faceImgAttachment = memberHealthRecordAttachmentService.selectList(new EntityWrapper<MemberHealthRecordAttachment>().eq("health_record", memberHealthRecordId).eq("distinction",3));
        List<MemberHealthRecordAttachment> tongueImgAttachment = memberHealthRecordAttachmentService.selectList(new EntityWrapper<MemberHealthRecordAttachment>().eq("health_record", memberHealthRecordId).eq("distinction",4));
        List<MemberHealthRecordAttachment> partImgAttachment = memberHealthRecordAttachmentService.selectList(new EntityWrapper<MemberHealthRecordAttachment>().eq("health_record", memberHealthRecordId).eq("distinction",5));
        if(hras.size()>0 ){
            model.addAttribute("document",hras.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        if(faceImgAttachment.size()>0 ){
            model.addAttribute("faceImgAttachment",faceImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        if(tongueImgAttachment.size()>0 ){
            model.addAttribute("tongueImgAttachment",tongueImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        if(partImgAttachment.size()>0 ){
            model.addAttribute("partImgAttachment",partImgAttachment.stream().map(h -> h.getPath()).collect(Collectors.toList()));
        }
        model.addAttribute("item",memberHealthRecord);
        LogObjectHolder.me().set(memberHealthRecord);
        return PREFIX + "memberHealthRecord_detail.html";
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/projectByMember")
    @ResponseBody
    public List<Map<String,Object>> projectByMember(@RequestParam("member")Integer member) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        Wrapper<MemberHealthRecord> wrapper = new EntityWrapper<MemberHealthRecord>();
        wrapper.eq("member",member);
        List<MemberHealthRecord> memberHealthRecords = memberHealthRecordService.selectList(wrapper);
        memberHealthRecords.forEach(mhr ->{
            Map<String,Object> result = new HashMap<>();
            result.put("id",mhr.getProject());
            result.put("name",mhr.getEvaluation());
            resultList.add(result);
        });
        return resultList;
    }

    /**
     * 选择健康方案
     */
    @RequestMapping("/solutions")
    public String solutions( Model model) {
        List<Dict> allProjectCategories = ConstantFactory.me().findAllProjectCategories();
        List<RProjectVO> rps = new ArrayList<>();
        for(Dict d : allProjectCategories){
            RProjectVO rp = new RProjectVO();
            rp.setId(d.getId());
            rp.setName(d.getName());
            Wrapper<Project> projectWrapper = new EntityWrapper<>();
            projectWrapper.eq("category",d.getNum());
            List<Project> projects = projectService.selectList(projectWrapper);
            rp.setRps(projects);
            rps.add(rp);
        }
        model.addAttribute("item",rps);
        return PREFIX + "memberHealthRecord_solutions.html";
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsOne")
    @ResponseBody
    public List<Project> solutionsOne(){
//        List<Dict> allProjectCategories = ConstantFactory.me().findAllProjectCategories();
//        List<RProjectVO> rps = new ArrayList<>();
//        for(Dict d : allProjectCategories){
//            RProjectVO rp = new RProjectVO();
//            rp.setId(d.getId());
//            rp.setName(d.getName());
//            Wrapper<Project> projectWrapper = new EntityWrapper<>();
//            projectWrapper.eq("category",d.getNum());
//            List<Project> projects = projectService.selectOne();
//            rp.setRps(projects);
//            rps.add(rp);
//        }
//        return rps;
        List<Project> projects = projectService.selectOne();
        return projects;
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsTwo")
    @ResponseBody
    public List<RProjectVO> solutionsTwo(){
        List<Dict> allProjectCategories = ConstantFactory.me().findAllProjectCategories();
        List<RProjectVO> rps = new ArrayList<>();
        for(Dict d : allProjectCategories){
            RProjectVO rp = new RProjectVO();
            rp.setId(d.getId());
            rp.setName(d.getName());
            Wrapper<Project> projectWrapper = new EntityWrapper<>();
            projectWrapper.eq("category",d.getNum());
            List<Project> projects = projectService.selectList(projectWrapper);
            rp.setRps(projects);
            rps.add(rp);
        }
        return rps;
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsThree")
    @ResponseBody
    public List<Project> solutionsThree(){
        List<Project> projects = projectService.selectThree();
        return projects;
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsFour")
    @ResponseBody
    public List<Project> solutionsFour(){
        List<Project> projects = projectService.selectTwo();
        return projects;
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsFive")
    @ResponseBody
    public List<Project> solutionsFive(){
        List<Project> projects = projectService.selectFour();
        return projects;
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/solutionsSix")
    @ResponseBody
    public List<Project> solutionsSix(){
        List<Project> projects = projectService.selectFive();
        return projects;
    }


    /**
     * 获取健康档案列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.deptId;
        List<MemberHealthRecord> memberHealthRecords=memberHealthRecordService.selectByThisDept(depId);
        if (shiroUser.getRoleList().contains(1)){ // 1 代表超级管理员角色
            memberHealthRecords = memberHealthRecordService.selectList(new EntityWrapper<MemberHealthRecord>()
                    .orderDesc(Collections.singletonList("create_time")));
        }

        return new MemberHealthRecordWrapper(memberHealthRecords).wrap();
    }

    /**
     * 搜索框提示
     */
    @RequestMapping(value = {"/search/{k}","/search"})
    @ResponseBody
    public Object search(@PathVariable(value = "k" , required = false) String k) {
//        selectByClinic
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");

        Integer depId = shiroUser.deptId;
        List<Map<String, Object>> result = null;
//        List<MembershipCard> membershipCardList = null;
        if (shiroUser.getRoleList().contains(1)){
            result = memberService.selectAllNoBy();
        }else{
            result = memberService.selectByClinicHealth(depId);
        }
        if (k != null){
            result = memberHealthRecordService.selectByName(k);
        }

        return result;
    }

    /**
     * 新增健康档案
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberHealthRecord memberHealthRecord,@RequestParam String doc,@RequestParam(value = "attachment[]")List<String> attachment,
                      @RequestParam(value = "faceImgAttachment[]",required = false)List<String> faceImgAttachment,
                      @RequestParam(value = "tongueImgAttachment[]",required = false)List<String> tongueImgAttachment,
                      @RequestParam(value = "partImgAttachment[]",required = false)List<String> partImgAttachment) {
        Member member = memberService.selectById(memberHealthRecord.getMember());
        if (member== null){
            return new ErrorTip(501, "添加失败，只能添加已经登记过的会员哦，请从下拉中选择所需要添加的会员");
        }
        memberHealthRecordService.insert(memberHealthRecord);
//        if(StringUtils.isNotEmpty(doc)){
//            MemberHealthRecordAttachment mhra = new MemberHealthRecordAttachment();
//            mhra.setHealthRecord(memberHealthRecord.getId());
//            mhra.setPath(doc);
//            memberHealthRecordAttachmentService.insert(mhra);
//        }
        // 附件 1代表康复体检报告    3代表脸部照片附件 ，4代表舌部照片附件，5代表局部照片附件
        memberHealthRecordAttachmentUpload(memberHealthRecord,attachment,1);

        memberHealthRecordAttachmentUpload(memberHealthRecord,faceImgAttachment,3);

        memberHealthRecordAttachmentUpload(memberHealthRecord,tongueImgAttachment,4);

        memberHealthRecordAttachmentUpload(memberHealthRecord,partImgAttachment,5);

        return super.SUCCESS_TIP;
    }

    private void memberHealthRecordAttachmentUpload(MemberHealthRecord memberHealthRecord, List<String> attachment,Integer distinction) {
        if(attachment!=null){
            attachment.forEach(a ->{
                MemberHealthRecordAttachment mhra = new MemberHealthRecordAttachment();
                mhra.setHealthRecord(memberHealthRecord.getId());
                mhra.setPath(a);
                mhra.setDistinction(distinction);
                memberHealthRecordAttachmentService.insert(mhra);
            });
        }
    }

    /**
     * 删除健康档案
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberHealthRecordId) {
        memberHealthRecordService.deleteById(memberHealthRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改健康档案
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberHealthRecord memberHealthRecord) {
        memberHealthRecordService.updateById(memberHealthRecord);
        return super.SUCCESS_TIP;
    }

    /**
     * 健康档案详情
     */
    @RequestMapping(value = "/detail/{memberHealthRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("memberHealthRecordId") Integer memberHealthRecordId) {
        return memberHealthRecordService.selectById(memberHealthRecordId);
    }

    /**
     * 对是否有权限为其他门店设备进行修改
     * @param model
     */
    private void fillIfCanMaintainForOtherDept(Model model) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_HR_EDIT_OTHER_DEPT)){
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

//    /**
//     * 根据id获取用户姓名和卡号
//     */
//    @RequestMapping(value = "memberHealthRecord/getNameAndCardById")
//    @ResponseBody
//    public Object getById(Integer id){
//        List<Member> members = memberService.selectHealthRecordById(id);
//        return members;
//    }

    /**
     * 选择健康方案
     */
    @PostMapping("/projectSup")
    @ResponseBody
    public Object list() {
        List<ProjectSup> projectSups = iProjectSupService.selectList();
        return new ProjectSupWrapper(projectSups).wrap();
    }

    /**
     * 选择健康方案
     */
    @PostMapping("/projectSecond")
    @ResponseBody
    public Object listSecond() {
        List<ProjectSecond> projectSeconds = iProjectSecondService.selectList();
        return new ProjectSecondWrapper(projectSeconds).wrap();
    }
}
