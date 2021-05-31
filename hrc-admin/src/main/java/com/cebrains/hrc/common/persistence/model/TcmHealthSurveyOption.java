package com.cebrains.hrc.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 中医体检报告表
 * </p>
 *
 * @author frank123
 * @since 2020-12-25
 */
@TableName("tcm_health_survey_option")
public class TcmHealthSurveyOption extends Model<TcmHealthSurveyOption> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类别
1.眼鼻
2.眼睛
3.身体
4.手脚
5.甲印
6.胃口
7.牙
8.舌
9.睡眠
10.出汗
11.小便
12.大便
13.脊椎压疼点
14.足三里压疼点
15.三阴交压疼点
16.跌阳脉
17.两性
     */
    private Integer category;
    /**
     * 提示
     */
    private String tips;
    /**
     * 类别细分
     */
    private String module;

    /**
     * 位置
     */
    private Integer location;

    /**
     * 显示字段，非存储字段
     */
    @TableField(exist = false)
    private String optionCategory;


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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TcmHealthSurveyOption{" +
        "id=" + id +
        ", name=" + name +
        ", category=" + category +
        ", tips=" + tips +
        ", module=" + module +
        ", location=" + location +
        "}";
    }

    public String getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
    }
}
