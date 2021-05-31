package com.cebrains.hrc.modular.resource.wrapper;


import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.MemberRehabilitationRecord;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

/**
 * 康复记录 包装类
 */
public class MemberRehabilitationRecordWrapper extends BaseCustomWarpper<MemberRehabilitationRecord> {


    public MemberRehabilitationRecordWrapper(List<MemberRehabilitationRecord> list) {
        super(list);
    }

    @Override
    protected void wrapTheObject(MemberRehabilitationRecord item) {
        item.setMemberName(ConstantFactory.me().getMemberUserName(item.getMemberUser()));
        item.setDepartmentName(ConstantFactory.me().getDeptName(item.getDepartment()));
    }
}
