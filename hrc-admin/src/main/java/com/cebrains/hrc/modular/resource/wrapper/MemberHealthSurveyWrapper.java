package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.MemberHealthSurvey;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

/**
 * 健康信息管理的包装类
 *
 *
 */
public class MemberHealthSurveyWrapper extends BaseCustomWarpper<MemberHealthSurvey> {

    public MemberHealthSurveyWrapper(List<MemberHealthSurvey> list) {
        super(list);
    }

    @Override
    protected void wrapTheObject(MemberHealthSurvey item) {
        item.setMemberAddress(ConstantFactory.me().getMemberAddress(item.getMember()));
        item.setCreatedByName(ConstantFactory.me().getUserNameById(item.getCreatedBy()));
        item.setMemberName(ConstantFactory.me().getMemberName(item.getMember()));
        item.setMemberPhone(ConstantFactory.me().getMemberPhone(item.getMember()));
        item.setMemberSex(ConstantFactory.me().getMemberSex(item.getMember()));
        item.setDepartmentName(ConstantFactory.me().getDeptName(item.getDepartment()));
    }
}
