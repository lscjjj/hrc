package com.cebrains.hrc.common.persistence.vo;

import com.cebrains.hrc.common.persistence.model.HealthSurveyOption;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import com.cebrains.hrc.common.persistence.model.Project;

import java.util.List;

public class MemberHealthSurveyVo {
    private Integer id;
    private String name;
    private List<HealthSurveyOption> rps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HealthSurveyOption> getRps() {
        return rps;
    }

    public void setRps(List<HealthSurveyOption> rps) {
        this.rps = rps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
