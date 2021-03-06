package com.cebrains.hrc.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.annotion.BussinessLog;
import com.cebrains.hrc.common.annotion.Permission;
import com.cebrains.hrc.common.constant.Const;
import com.cebrains.hrc.common.constant.dictmap.UserDict;
import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.constant.state.ManagerStatus;
import com.cebrains.hrc.common.exception.BizExceptionEnum;
import com.cebrains.hrc.common.persistence.dao.UserMapper;
import com.cebrains.hrc.common.persistence.model.MemberUser;
import com.cebrains.hrc.common.persistence.model.User;
import com.cebrains.hrc.common.persistence.vo.KeyAndValueVo;
import com.cebrains.hrc.config.properties.HRCProperties;
import com.cebrains.hrc.core.base.controller.BaseController;
import com.cebrains.hrc.core.base.tips.Tip;
import com.cebrains.hrc.core.datascope.DataScope;
import com.cebrains.hrc.core.db.Db;
import com.cebrains.hrc.core.exception.HRCException;
import com.cebrains.hrc.core.log.LogObjectHolder;
import com.cebrains.hrc.core.shiro.ShiroKit;
import com.cebrains.hrc.core.shiro.ShiroUser;
import com.cebrains.hrc.core.util.ToolUtil;
import com.cebrains.hrc.modular.member.service.IMemberUserService;
import com.cebrains.hrc.modular.system.dao.UserMgrDao;
import com.cebrains.hrc.modular.system.factory.UserFactory;
import com.cebrains.hrc.modular.system.transfer.UserDto;
import com.cebrains.hrc.modular.system.warpper.UserWarpper;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.File;
import java.util.*;

/**
 * ????????????????????????
 *
 * @author frank
 * @Date 2017???1???11??? ??????1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/system/user/";

    @Resource
    private HRCProperties hrcProperties;

    @Resource
    private UserMgrDao managerDao;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private IMemberUserService memberUserService;

    /**
     * ???????????????????????????????????????
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * ???????????????????????????????????????
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * ???????????????????????????
     */
    //@RequiresPermissions("/mgr/role_assign")  //??????shiro?????????????????????
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * ??????????????????????????????
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userMapper.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * ?????????????????????????????????
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    /**
     * ???????????????????????????
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * ???????????????????????????
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new HRCException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new HRCException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * ?????????????????????
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        if (ShiroKit.isAdmin()) {
            List<Map<String, Object>> users = managerDao.selectUsers(null, name, beginTime, endTime, deptid);
            return new UserWarpper(users).wrap();
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            List<Map<String, Object>> users = managerDao.selectUsers(dataScope, name, beginTime, endTime, deptid);
            return new UserWarpper(users).wrap();
        }
    }

    /**
     * ????????????????????????
     */
    @PostMapping("/userByDepartment")
    @ResponseBody
    public Object userByDepartment(@RequestParam Integer dept) {
        List<KeyAndValueVo> result = new ArrayList<>();
        Wrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper = userWrapper.eq("deptid", dept);
        List<User> users = userMapper.selectList(userWrapper);
        if(users!=null)
            users.forEach(u -> result.add(new KeyAndValueVo(u.getName(),String.valueOf(u.getId()))));
        return result;
    }

    /**
     * ???????????????
     */
    @RequestMapping("/add")
    @BussinessLog(value = "???????????????", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }

        // ????????????????????????
        User theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new HRCException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // ??????????????????
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());

        this.userMapper.insert(UserFactory.createUser(user));
        return SUCCESS_TIP;
    }

    /**
     * ???????????????
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "???????????????", key = "account", dict = UserDict.class)
    @ResponseBody
    public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.userMapper.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            assertAuth(user.getId());
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser.getId().equals(user.getId())) {
                this.userMapper.updateById(UserFactory.createUser(user));
                return SUCCESS_TIP;
            } else {
                throw new HRCException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "???????????????", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        //???????????????????????????
        if (userId.equals(Const.ADMIN_ID)) {
            throw new HRCException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * ?????????????????????
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.userMapper.selectById(userId);
    }

    /**
     * ????????????????????????
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "?????????????????????", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userMapper.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userMapper.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * ????????????
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "????????????", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        //???????????????????????????
        if (userId.equals(Const.ADMIN_ID)) {
            throw new HRCException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * ??????????????????
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "??????????????????", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * ????????????
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "????????????", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new HRCException(BizExceptionEnum.REQUEST_NULL);
        }
        //???????????????????????????
        if (userId.equals(Const.ADMIN_ID)) {
            throw new HRCException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        //?????????????????????
        if (roleIds.contains("22")&&roleIds.length()==2){
            User user=this.userMapper.selectById(userId);
            MemberUser memberUser= new MemberUser();
            memberUser.setGender(user.getSex());
            memberUser.setId(userId);
            memberUser.setMemberUser(userId);
            memberUser.setBirthday(user.getBirthday());
            memberUser.setPhone(user.getPhone());
            memberUser.setEmail(user.getEmail());
            memberUser.setName(user.getName());
            memberUserService.insert(memberUser);
        }
        this.managerDao.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * ????????????(??????????????????webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = hrcProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new HRCException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * ????????????(??????????????????webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/docUpload")
    @ResponseBody
    public String docUpload(@RequestPart("file") MultipartFile doc) {
        String docName = UUID.randomUUID().toString()+"." + FilenameUtils.getExtension(doc.getOriginalFilename());
        try {
            String fileSavePath = hrcProperties.getFileUploadPath();
            System.out.println(fileSavePath);
            File docFile = new File(fileSavePath,"doc");
            if(!docFile.exists()){
                docFile.mkdirs();
            }

            doc.transferTo(new File(docFile +"/" + docName));
        } catch (Exception e) {
            throw new HRCException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return "/docd/"+docName;
    }

    /**
     * ???????????????????????????????????????????????????????????????
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
