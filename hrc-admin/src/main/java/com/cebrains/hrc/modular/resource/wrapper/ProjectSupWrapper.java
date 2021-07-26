package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.persistence.model.History;
import com.cebrains.hrc.common.persistence.model.ProjectSup;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

public class ProjectSupWrapper  extends BaseCustomWarpper<ProjectSup> {

    public ProjectSupWrapper(List<ProjectSup> list) {
        super(list);
    }
    @Override
    protected void wrapTheObject(ProjectSup map) {

    }
}
