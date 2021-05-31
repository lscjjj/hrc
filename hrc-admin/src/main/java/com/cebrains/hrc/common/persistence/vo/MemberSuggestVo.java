package com.cebrains.hrc.common.persistence.vo;

public class MemberSuggestVo {
    private Integer id;
    private String name;
    private String mobile;
    private String idCard;

    public MemberSuggestVo(Integer id, String name, String mobile, String idCard) {
        this.id=id;
        this.name=name;
        this.mobile=mobile;
        this.idCard = idCard;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
