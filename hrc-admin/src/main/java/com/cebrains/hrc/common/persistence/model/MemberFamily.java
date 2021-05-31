package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("member_family")
public class MemberFamily extends Model<MemberFamily> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("member_user")
    private Integer memberUser;
    /**
     * 称呼
     */
    private String appellation;
    /**
     * 性别 1 男 2女
     */
    private Integer gender;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 既往病史
     */
    @TableField("medical_history")
    private String medicalHistory;

    @TableField("create_time")
    private Date createTime;

    /**
     * 非存储字段 字典
     * @return
     */
    @TableField(exist = false)
    private String genderName;

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

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MemberFamily{" +
                "id=" + id +
                ", memberUser=" + memberUser +
                ", appellation='" + appellation + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}
