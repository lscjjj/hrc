package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.MemberHealthConsultation;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

/**
 * 健康咨询包装类
 */
public class MemberHealthConsultationWrapper extends BaseCustomWarpper<MemberHealthConsultation> {

    public MemberHealthConsultationWrapper(List<MemberHealthConsultation> list) {
        super(list);
    }

    @Override
    protected void wrapTheObject(MemberHealthConsultation item) {
//        item.setCreatedByName(ConstantFactory.me().getUserNameById(item.getCreatedBy()));
        item.setDepartmentName(ConstantFactory.me().getDeptName(item.getDepartment()));
        item.setStateName(ConstantFactory.me().getStateName(item.getState()));
        item.setFamilyName(ConstantFactory.me().getFamilyName(item.getFamily()));
        item.setAppellationName(ConstantFactory.me().getAppellationName(item.getFamily()));
        item.setMemberUserName(ConstantFactory.me().getMemberUserName(item.getMemberUser()));
        item.setMemberUserPhone(ConstantFactory.me().getMemberUserPhone(item.getMemberUser()));
    }
}
