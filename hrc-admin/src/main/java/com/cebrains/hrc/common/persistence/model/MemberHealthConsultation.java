package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("member_health_consultation")
public class MemberHealthConsultation extends Model<MemberHealthConsultation> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员
     */
    @TableField("member_user")
    private Integer memberUser;

    /**
     * 咨询成员
     */
    @TableField("family")
    private Integer family;

    /**
     * 咨询门店
     */
    private Integer department;
    /**
     * 症状表现
     */
    private String symptoms;

    /**
     * 咨询内容
     */
    @TableField("consultation_info")
    private String consultationInfo;
    /**
     * 操作员
     */
    @TableField("created_by")
    private String createdBy;
    /**
     * 咨询回复内容
     */
    @TableField("consultation_suggest")
    private String consultationSuggest;
    /**
     * 建议使用的康复产品
     */
    @TableField("suggest_produce")
    private String suggestProduce;
    /**
     * 状态 1 未回复，2已回复
     */
    private Integer state;

    @TableField("create_time")
    private Date createTime;

    /**
     * 非存储字段
     *
     */
    @TableField(exist = false)
    private String createdByName;
    @TableField(exist = false)
    private String departmentName;
    @TableField(exist = false)
    private String stateName;
    @TableField(exist = false)
    private String familyName;
    @TableField(exist = false)
    private String appellationName;
    @TableField(exist = false)
    private String memberUserName;
    @TableField(exist = false)
    private String memberUserPhone;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberUser() {
        return memberUser;
    }

    public void setMemberUser(Integer memberUser) {
        this.memberUser = memberUser;
    }

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getConsultationInfo() {
        return consultationInfo;
    }

    public void setConsultationInfo(String consultationInfo) {
        this.consultationInfo = consultationInfo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getConsultationSuggest() {
        return consultationSuggest;
    }

    public void setConsultationSuggest(String consultationSuggest) {
        this.consultationSuggest = consultationSuggest;
    }

    public String getSuggestProduce() {
        return suggestProduce;
    }

    public void setSuggestProduce(String suggestProduce) {
        this.suggestProduce = suggestProduce;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MemberHealthConsultation{" +
                "id=" + id +
                ", memberUser=" + memberUser +
                ", family=" + family +
                ", department=" + department +
                ", symptoms='" + symptoms + '\'' +
                ", consultationInfo='" + consultationInfo + '\'' +
                ", createdBy=" + createdBy +
                ", consultationSuggest='" + consultationSuggest + '\'' +
                ", suggestProduce='" + suggestProduce + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getAppellationName() {
        return appellationName;
    }

    public void setAppellationName(String appellationName) {
        this.appellationName = appellationName;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public String getMemberUserPhone() {
        return memberUserPhone;
    }

    public void setMemberUserPhone(String memberUserPhone) {
        this.memberUserPhone = memberUserPhone;
    }
}
