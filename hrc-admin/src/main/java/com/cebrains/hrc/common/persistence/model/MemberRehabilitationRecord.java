package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("member_rehabilitation_record")
public class MemberRehabilitationRecord extends Model<MemberRehabilitationRecord> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员
     */
    @TableField("member_user")
    private Integer memberUser;
    /**
     * 使用地点
     */
    private Integer department;
    /**
     * 使用产品
     */
    private String produce;
    /**
     * 使用感受
     */
    private String feel;

    @TableField("create_time")
    private Date createTime;


    /**
     * 非存储字段
     */
    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String departmentName;


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

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MemberRehabilitationRecord{" +
                "id=" + id +
                ", memberUser=" + memberUser +
                ", department='" + department + '\'' +
                ", produce='" + produce + '\'' +
                ", feel='" + feel + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
