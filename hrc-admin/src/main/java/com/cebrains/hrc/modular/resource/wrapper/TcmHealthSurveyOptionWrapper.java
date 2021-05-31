package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.TcmHealthSurveyOption;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

public class TcmHealthSurveyOptionWrapper extends BaseCustomWarpper<TcmHealthSurveyOption> {

    public TcmHealthSurveyOptionWrapper(List<TcmHealthSurveyOption> list) {
        super(list);
    }

    @Override
    protected void wrapTheObject(TcmHealthSurveyOption item) {
        item.setOptionCategory(ConstantFactory.me().getTcmHealthSurveyOptionCategory(item.getCategory()));
    }
}
