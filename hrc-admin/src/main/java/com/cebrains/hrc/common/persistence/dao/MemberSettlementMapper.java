package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.MemberSettlement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员结算 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-05-15
 */
public interface MemberSettlementMapper extends BaseMapper<MemberSettlement> {

    Integer userExperienced(@Param("tid") Integer treatment);

    List<Map<String,Object>> treatmentPriceInformation(@Param("tid")Integer treatment);

    Integer queryMembershipBalanceByTreatmentId(@Param("tid")Integer treatment);

    void deductMoneyByMemberShipCard(Integer id);

    List<MemberSettlement> selectByDepartment(@Param("did") Integer departmentId);

    List<Map<String,Object>> selectAllSettlement();

    List<Map<String,Object>> selectSettlementByName(@Param("key") String key);

    @Select("SELECT ms.id," +
            "ms.treatment," +
            "ms.payment_method paymentMethod," +
            "ms.membership_card membershipCard," +
            "ms.create_time createTime," +
            "ms.payment_amount paymentAmount," +
            "ms.member_rating memberRating," +
            "ms.foreground_rating foregroundRating," +
            "ms.technician_rating technicianRating," +
            "ms.manager_rating managerRating," +
            "ms.member," +
            "ms.created_by createdBy\n" +
            "FROM member_settlement ms\n" +
            "  LEFT JOIN member m ON m.id = ms.member " +
            "WHERE m.id IS NOT NULL and m.clinic = #{departmentId}\n" +
            "ORDER BY ms.create_time DESC")
    List<MemberSettlement> selectByThisDepartment(Integer departmentId);
}
