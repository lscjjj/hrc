package com.cebrains.hrc.modular.resource.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.HealthSurveyOptionMapper;
import com.cebrains.hrc.common.persistence.model.HealthSurveyOption;
import com.cebrains.hrc.modular.resource.service.IHealthSurveyOptionService;
import org.springframework.stereotype.Service;

/**
 * 会员健康状况调查表 服务实现类
 */
@Service
public class HealthSurveyOptionImpl extends ServiceImpl<HealthSurveyOptionMapper, HealthSurveyOption> implements IHealthSurveyOptionService {
}
