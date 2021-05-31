package com.cebrains.hrc.common.persistence.vo;


public class MemberFamilySuggestVo {

    private Integer id;
    private String appellation;
    private String name;

    public MemberFamilySuggestVo(Integer id, String appellation, String name) {
        this.id = id;
        this.appellation=appellation;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
