package com.cebrains.hrc.modular.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.factory.IConstantFactory;
import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.common.persistence.model.MemberFamily;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.common.persistence.vo.MemberFamilySuggestVo;
import com.cebrains.hrc.common.persistence.vo.MemberSuggestVo;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.modular.member.service.IMemberFamilyService;
import com.cebrains.hrc.modular.member.service.impl.MemberUserServiceImpl;
import com.cebrains.hrc.modular.resource.wrapper.MemberFamilyWrapper;
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
 * 家庭成员 控制器
 */
@Controller
@RequestMapping("memberFamily")
public class MemberFamilyController extends BaseController {

    /**
     * 前缀
     */
    private static String PREFIX = "/member/memberFamily/";

    @Autowired
    private IMemberFamilyService memberFamilyService;

    @Autowired
    private MemberUserServiceImpl memberUserService;

    /**
     * 跳转到家庭成员首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "memberFamily.html";
    }

    /**
     * 跳转到添加会员健康状况调查
     */
    @RequestMapping("/memberFamily_add")
    public String memberHealthSurveyAdd(Model model) {

        model.addAttribute("genderDict", ConstantFactory.me().findDictByKey(IConstantFactory.DICT_KEY_GNDR));

        return PREFIX + "memberFamily_add.html";
    }

    /**
     * 新增家庭成员
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberFamily memberFamily) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        memberFamily.setMemberUser(user.id);
        memberFamilyService.insert(memberFamily);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除家庭成员
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberFamilyId) {
        memberFamilyService.deleteById(memberFamilyId);
        return SUCCESS_TIP;
    }

    /**
     * 获取家庭成员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id= user.id;
        List<MemberFamily> memberFamilies = memberFamilyService.selectList(new EntityWrapper<MemberFamily>()
            .eq("member_user",id));
        return new MemberFamilyWrapper(memberFamilies).wrap();
    }

    /**
     * 获取家庭成员信息
     */
    @RequestMapping(value = "/infoById")
    @ResponseBody
    public Object infoById(@RequestParam("id")Integer id) {
        MemberFamily memberFamily = memberFamilyService.selectById(id);
        return memberFamily;
    }

    /**
     * 获取家庭成员列表
     */
    @RequestMapping(value = "/suggestList/{k}")
    @ResponseBody
    public Object suggestList(@PathVariable String k) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id= user.id;
        List<MemberFamilySuggestVo> result = new ArrayList<>();
        Wrapper<MemberFamily> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("appellation", "%"+k+"%");
        List<MemberFamily> memberFamilies = memberFamilyService.selectList(new EntityWrapper<MemberFamily>()
            .eq("member_user",id));
        if(memberFamilies!=null){
            memberFamilies.forEach(m -> result.add(new MemberFamilySuggestVo(m.getId(),m.getAppellation(),m.getName())));
        }
        return result;
    }
}
