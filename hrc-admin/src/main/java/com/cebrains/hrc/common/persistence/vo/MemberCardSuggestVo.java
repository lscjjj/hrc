package com.cebrains.hrc.common.persistence.vo;

public class MemberCardSuggestVo {
    private String number;
    private String name;
    private String mobile;
    private String idCard;

    public MemberCardSuggestVo(String number, String name, String mobile, String idCard) {
        this.number = number;
        this.name = name;
        this.mobile = mobile;
        this.idCard = idCard;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
