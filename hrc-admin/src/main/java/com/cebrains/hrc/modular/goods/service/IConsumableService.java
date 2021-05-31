package com.cebrains.hrc.modular.goods.service;

import com.cebrains.hrc.common.persistence.model.Consumable;
import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.MemberFamily;

import java.util.List;

/**
 * <p>
 * 耗材管理 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-05-10
 */
public interface IConsumableService extends IService<Consumable> {

    List<Consumable> selectByDeptId(Integer deptid);
}
