package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.persistence.model.MembershipChargeLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员卡充值记录 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-04-17
 */
public interface IMembershipChargeLogService extends IService<MembershipChargeLog> {

    List<MembershipChargeLog> selectThisDept(Integer depId);

    List<MembershipChargeLog> selectLogList();

    void deleteLog(Integer id);
}
