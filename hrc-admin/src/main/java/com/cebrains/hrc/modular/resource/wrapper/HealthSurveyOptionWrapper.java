package com.cebrains.hrc.modular.resource.wrapper;

import com.cebrains.hrc.common.constant.factory.ConstantFactory;
import com.cebrains.hrc.common.persistence.model.HealthSurveyOption;
import com.cebrains.hrc.core.base.warpper.BaseCustomWarpper;

import java.util.List;

/**
 * 会员健康状况调查表包装类
 */
public class HealthSurveyOptionWrapper extends BaseCustomWarpper<HealthSurveyOption> {

    public HealthSurveyOptionWrapper(List<HealthSurveyOption> list) {
        super(list);
    }

    /**
     * 通过字典获取对应的体质类型值
     * @param map
     */
    @Override
    protected void wrapTheObject(HealthSurveyOption map) {
        map.setOptionCategory(ConstantFactory.me().getHealthSurveyOptionCategory(map.getCategory()));
    }
}
