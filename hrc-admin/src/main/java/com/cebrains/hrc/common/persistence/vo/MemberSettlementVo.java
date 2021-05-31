package com.cebrains.hrc.common.persistence.vo;

public class MemberSettlementVo {
    private Integer id;
    private String name;

    public MemberSettlementVo(Integer id, String name){
        this.id = id;
        this.name = name;
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
}
