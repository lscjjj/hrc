package com.cebrains.hrc.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员健康调查表实体类
 */
@TableName("health_survey_option")
public class HealthSurveyOption extends Model<HealthSurveyOption> implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 选项名称
     */
    private String name;
    /**
     * 选项类别
     */
    private Integer category;
    /**
     * 提示
     */
    private String tips;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 模块，1：在第一个模块，2：在第二个模块,3:在第三个模块
     */
    private Integer module;
    /**
     * 显示字段
     * 调查选项类别
     * @return
     */
    @TableField(exist = false)
    private String optionCategory;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "HealthSurveyOption{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", tips='" + tips + '\'' +
                ", createTime=" + createTime +
                ", module=" + module +
                '}';
    }

    public String getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
    }
}
