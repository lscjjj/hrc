package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.persistence.model.MembershipCard;
import com.cebrains.hrc.common.persistence.model.MembershipChargeLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员卡充值记录 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-04-17
 */
public interface MembershipChargeLogMapper extends BaseMapper<MembershipChargeLog> {

    @Select("select " +
            "mcl.*," +
            "mcl.create_time createTime, " +
            "mcl.given_amount givenAmount, " +
            "mcl.remaining_amount remainingAmount, " +
            "mcl.amount amount " +
            "from membership_charge_log mcl " +
            "inner join membership_card mc " +
            "on mcl.card=mc.id " +
            "inner join member m " +
            "on m.id = mc.user " +
            "where m.clinic=#{depId} and mcl.delete_log = 1 ORDER BY mcl.create_time desc")
    List<MembershipChargeLog> selectThisDept(Integer depId);

    List<MembershipChargeLog> selectLogList();

    void deleteLog(Integer id);
}
