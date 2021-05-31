package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.ehcache.config.SizedResourcePool;

import java.io.Serializable;
import java.util.Date;

@TableName("member_health_survey")
public class MemberHealthSurvey extends Model<MemberHealthSurvey> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员
     */
    private Integer member;
    /**
     *会员账号
     */
    @TableField("member_user")
    private Integer memberUser;
    /**
     * 年龄
     */
    private Integer age;
    /**
     *调查信息
     */
    @TableField("survey_info")
    private String surveyInfo;

    /**
     * 建表门店
     */
    private Integer department;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作员
     */
    @TableField("created_by")
    private Integer createdBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 非存储字段，配合字典
     */
    @TableField(exist = false)
    private String createdByName;
    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String memberPhone;
    @TableField(exist = false)
    private String memberSex;
    @TableField(exist = false)
    private String memberAddress;
    @TableField(exist = false)
    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getMemberUser() {
        return memberUser;
    }

    public void setMemberUser(Integer memberUser) {
        this.memberUser = memberUser;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSurveyInfo() {
        return surveyInfo;
    }

    public void setSurveyInfo(String surveyInfo) {
        this.surveyInfo = surveyInfo;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MemberHealthSurvey{" +
                "id=" + id +
                ", member=" + member +
                ", memberUser=" + memberUser +
                ", age=" + age +
                ", surveyInfo='" + surveyInfo + '\'' +
                ", department=" + department +
                ", remark='" + remark + '\'' +
                ", createdBy=" + createdBy +
                ", createTime=" + createTime +
                '}';
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
