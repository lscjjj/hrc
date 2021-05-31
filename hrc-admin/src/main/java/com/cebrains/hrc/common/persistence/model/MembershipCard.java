package com.cebrains.hrc.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 会员卡
 * </p>
 *
 * @author frank123
 * @since 2018-04-16
 */
@TableName("membership_card")
public class MembershipCard extends Model<MembershipCard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员卡号
     */
    private String number;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 所属用户
     */
    private Integer user;
    /**
     * 会员卡级别
     */
    private Integer level;
    /**
     * 绑定项目
     */
    private Integer project;
    /**
     * 会员折扣
     */
    private Integer discount;
    /**
     * 过期时间
     */
    @TableField("due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    /**
     * 创建门店
     */
    private Integer department;
    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 建档人员
     */
    @TableField("created")
    private Integer created;
    /**
     * 总赠送金额
     */
    @TableField("given_amount")
    private Double givenAmount;
    /**
     * 总充值金额
     */
    @TableField("amount")
    private Double amount;
    /**
     * 非存储字段
     */
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String levelName;
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private String discountText;
    @TableField(exist = false)
    private String departmentName;
    @TableField(exist = false)
    private String idCard;
    @TableField(exist = false)
    private String createdName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Double getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(Double givenAmount) {
        this.givenAmount = givenAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MembershipCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", user=" + user +
                ", level=" + level +
                ", project=" + project +
                ", discount=" + discount +
                ", dueDate=" + dueDate +
                ", department=" + department +
                ", createTime=" + createTime +
                ", created=" + created +
                ", givenAmount=" + givenAmount +
                ", amount=" + amount +
                '}';
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscountText(String discountText) {
        this.discountText = discountText;
    }

    public String getDiscountText() {
        return discountText;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }
}
