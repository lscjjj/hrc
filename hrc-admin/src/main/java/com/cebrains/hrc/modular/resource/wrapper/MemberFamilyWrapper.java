package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.MemberFamily;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

/**
 * 家庭成员包装类
 */
public class MemberFamilyWrapper extends BaseCustomWarpper<MemberFamily> {

    public MemberFamilyWrapper(List<MemberFamily> list) {
        super(list);
    }

    @Override
    protected void wrapTheObject(MemberFamily item) {
        item.setGenderName(ConstantFactory.me().getSexName(item.getGender()));
    }
}
