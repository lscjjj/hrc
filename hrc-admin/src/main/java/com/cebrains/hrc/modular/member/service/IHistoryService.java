package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.History;
import com.cebrains.hrc.common.persistence.model.Member;

import java.util.List;

/**
 * <p>
 * 会员修改历史表 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface IHistoryService extends IService<History> {

    List<History> selectList(Integer id);
}
