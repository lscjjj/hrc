package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.History;
import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

public class HistoryWrapper extends BaseCustomWarpper<History>{

/**
 * 项目管理的包装类
 *
 * @author frank
 * @date 2017年2月13日 下午10:47:03
 */

    public HistoryWrapper(List<History> list) {
        super(list);
    }


    @Override
    protected void wrapTheObject(History map) {

    }
}

