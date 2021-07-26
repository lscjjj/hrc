package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.dao.DeptMapper;
import com.cebrains.hrc.common.persistence.model.Dept;
import com.cebrains.hrc.common.persistence.model.History;
import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.common.persistence.vo.MemberSuggestVo;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.base.tips.ErrorTip;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IHistoryService;
import com.cebrains.hrc.modular.member.service.IMemberService;
import com.cebrains.hrc.modular.resource.wrapper.HistoryWrapper;
import com.cebrains.hrc.modular.resource.wrapper.MemberWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 会员控制器
 *
 * @author frank
 * @Date 2018-03-08 11:10:55
 */
@Controller
@RequestMapping("/oldMember")
public class OldMemberController extends BaseController {

    private String PREFIX = "/oldMember/oldMember/";

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IHistoryService historyService;

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 跳转到会员首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "oldMember.html";
    }

    /**
     * 跳转到添加会员
     */
    @RequestMapping("/member_add")
    public String memberAdd(Model model) {
        model.addAttribute("genderDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_GNDR));
        model.addAttribute("bloodTypeDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_BLT));
//        model.addAttribute("occupationDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_OCCUPATION));
        model.addAttribute("maritalStatusDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_MST));
        model.addAttribute("yesOrNoDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_YON));
        model.addAttribute("preferredNursingTimeDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_PNT));
        model.addAttribute("yesOrNoDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_YON));
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
        Integer departmentId = shiroUser.getDeptId();
        Dept dept = deptMapper.selectById(departmentId);
        if(dept != null && dept.getIsStore()!=null && dept.getIsStore()==1) {
            model.addAttribute("department", dept.getId());
            model.addAttribute("departmentName", dept.getSimplename());
            model.addAttribute("fixedDept",1);
        }else{
            model.addAttribute("department", "");
            model.addAttribute("departmentName", "");
            model.addAttribute("fixedDept",0);
        }
        return PREFIX + "oldMember_add.html";
    }

    /**
     * 跳转到修改会员列表首页
     */
    @RequestMapping("/history_list/{id}")
    public Object history(@PathVariable Integer id) {
        return PREFIX + "oldHistory.html";
    }

    /**
     * 跳转到修改会员
     */
    @RequestMapping("/member_update/{memberId}")
    public String memberUpdate(@PathVariable Integer memberId, Model model) {
        Member member = memberService.selectById(memberId);
        member.setClinicName(ConstantFactory.me().getDeptName(member.getClinic()));
        member.setIntroducerName(ConstantFactory.me().getMemberName(member.getId()));
        model.addAttribute("item",member);
        model.addAttribute("genderDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_GNDR));
        model.addAttribute("bloodTypeDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_BLT));
//        model.addAttribute("occupationDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_OCCUPATION));
        model.addAttribute("maritalStatusDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_MST));
        model.addAttribute("yesOrNoDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_YON));
        model.addAttribute("preferredNursingTimeDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_PNT));
        model.addAttribute("yesOrNoDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_YON));
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
        Integer departmentId = shiroUser.getDeptId();
        Dept dept = deptMapper.selectById(departmentId);
        if(dept != null && dept.getIsStore()!=null && dept.getIsStore()==1) {
            model.addAttribute("fixedDept",1);
        }else{
            model.addAttribute("fixedDept",0);
        }
        LogObjectHolder.me().set(member);
        return PREFIX + "oldMember_edit.html";

    }

    /**
     * 获取会员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
        Integer depId = shiroUser.deptId;
        List<Member> members =null;
        if(shiroUser.roleList.contains(1)){      //只有超级管理员才能看到所有会员信息
            members = memberService.selectList(new EntityWrapper<Member>()
                    .orderDesc(Collections.singletonList("create_time")));
        }else{
            members = memberService.selectList(new EntityWrapper<Member>()
                    .eq("clinic",depId)
                    .orderDesc(Collections.singletonList("create_time")));
        }
//        List<Member> members = memberService.selectList(null);
        return new MemberWrapper(members).wrap();
    }

    /**
     * 获取修改会员历史列表
     */
    @RequestMapping(value = "/update_history_list/{id}")
    @ResponseBody
    public Object historyList(@PathVariable Integer id){
        List<History> histories = historyService.selectList(id);
        return new HistoryWrapper(histories).wrap();
    }

    /**
     * 获取会员信息
     */
    @RequestMapping(value = "/infoById")
    @ResponseBody
    public Object infoById(@RequestParam("id")Integer id) {
        Member member = memberService.selectById(id);
        return member;
    }

    /**
     * 获取会员建议列表
     */
    @RequestMapping(value = {"/suggestList/{k}","/suggestList"})
    @ResponseBody
    public Object suggestList(@PathVariable(value = "k",required = false) String k) {
        List<MemberSuggestVo> result = new ArrayList<>();
        Wrapper<Member> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("real_name", "%"+k+"%");
//        List<Member> members = null;
//            members = memberService.selectList(null);
//            if (k!=null){
//                members = memberService.selectList(wrapper);
//            }
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
        Integer depId = shiroUser.deptId;
        List<Member> members =null;
        if(shiroUser.roleList.contains(1)){      //只有超级管理员才能看到所有会员信息
            members = memberService.selectList(new EntityWrapper<Member>()
                    .orderDesc(Collections.singletonList("create_time")));
        }else{
            members = memberService.selectList(new EntityWrapper<Member>()
                    .eq("clinic",depId)
                    .orderDesc(Collections.singletonList("create_time")));
        }
        if(members!=null){
            members.forEach(m -> result.add(new MemberSuggestVo(m.getId(),m.getRealName(),m.getPhone(), m.getIdCard())));
        }
        return result;
    }

    /**
     * 获取会员建议列表
     */
    @RequestMapping(value = "/getAllMember")
    @ResponseBody
    public Object getAllMember() {
        List<MemberSuggestVo> result = new ArrayList<>();
        Wrapper<Member> wrapper = new EntityWrapper<>();
//        wrapper = wrapper.like("real_name", "%"+k+"%");
        List<Member> members = memberService.selectList(null);
        if(members!=null){
            members.forEach(m -> result.add(new MemberSuggestVo(m.getId(),m.getRealName(),m.getPhone(), m.getIdCard())));
        }
        return result;
    }

    /**
     * 根据名字模糊查询信息
     */
    @RequestMapping(value = "/selectByName")
    @ResponseBody
    public Object selectByName(String realName){
        List<Member> members = null;
        members = memberService.selectByName(realName);
        return members;
    }

    /**
     * 新增会员
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Member member) {
//        boolean memberExists = memberService.selectCount(new EntityWrapper<Member>().eq("phone", member.getPhone()).or().eq("id_card", member.getIdCard()))>0;
        boolean memberExists = memberService.selectCount(new EntityWrapper<Member>().eq("phone", member.getPhone()))>0;
        if(memberExists)
            return new ErrorTip(400,"电话号码已注册");
        member.setCreatedBy(((ShiroUser) getSession().getAttribute("shiroUser")).getId());
        memberService.insert(member);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除会员
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberId) {
        if(canDelete(memberId)==0){
            return new ErrorTip(401,"无法删除其他部门的会员!");
        }
        memberService.deleteById(memberId);
        return SUCCESS_TIP;
    }

    /**
     * 是否可以删除会员
     */
    @RequestMapping(value = "/canDelete")
    @ResponseBody
    public Integer canDelete(@RequestParam Integer memberId) {
        if(ShiroKit.lacksPermission(IConstantFactory.PERMISSION_DELETE_OTHER_DEPT_MEMBER)){ // 不能删除其他门店会员
            Member member = memberService.selectById(memberId);
            if(member==null || ((ShiroUser) getSession().getAttribute("shiroUser")).getDeptId()!=member.getClinic()){
                return 0;
            }
        }
        memberService.deleteById(memberId);
        return 1;
    }

    /**
     * 修改会员
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Member member) {
        String phone = member.getPhone();
        List<Member> members = memberService.getPhone(phone);
        if(members!=null){
            if(members.get(0).getId().intValue() != member.getId().intValue()){
                return new ErrorTip(400,"电话号码已注册");
            }
        }
        if(member.getId()==member.getIntroducer()){
            return new ErrorTip(400,"不能自我推荐哦");
        }
        History history = new History();
        BeanUtils.copyProperties(member,history);
        history.setUid(member.getId());
        ShiroUser shiroUser = (ShiroUser) getSession().getAttribute("shiroUser");
        Integer departmentId = shiroUser.getDeptId();
        Integer id = shiroUser.getId();
        history.setCreatedBy(id);
        Dept dept = deptMapper.selectById(departmentId);
        if(dept != null && dept.getIsStore()!=null && dept.getIsStore()==1) {
            member.setClinic(null);
        }
        historyService.insert(history);
        memberService.updateById(member);
        return super.SUCCESS_TIP;
    }

    /**
     * 会员详情
     */
    @RequestMapping(value = "/detail/{memberId}")
    @ResponseBody
    public Object detail(@PathVariable("memberId") Integer memberId) {
        return memberService.selectById(memberId);
    }


}

