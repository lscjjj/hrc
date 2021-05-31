package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("member_health_consultation_attachment")
public class MemberHealthConsultationAttachment extends Model<MemberHealthConsultationAttachment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 咨询内容
     */
    @TableField("health_consultation")
    private Integer healthConsultation;
    /**
     * 存储地址
     */
    private String path;
    @TableField("create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHealthConsultation() {
        return healthConsultation;
    }

    public void setHealthConsultation(Integer healthConsultation) {
        this.healthConsultation = healthConsultation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        return "MemberHealthConsultationAttachment{" +
                "id=" + id +
                ", healthConsultation=" + healthConsultation +
                ", path='" + path + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
