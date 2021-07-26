package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.common.persistence.model.MembershipCard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员卡 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-04-16
 */
public interface MembershipCardMapper extends BaseMapper<MembershipCard> {

    List<Map<String,Object>> selectSuggestCardList(@Param("key") String key);

    void deductMoneyByTreatment(@Param("tid")Integer treatment, @Param("amount")Double paymentAmount);

    void deductMoneyByMemberShipCard(@Param("membershipCard")Integer id,@Param("amount")Double paymentAmount);

    void deductMoneyByTransferCardConsumption(@Param("tid")String treatment, @Param("amount")Double paymentAmount);

    void moneyByTransferCardConsumption( @Param("tid")String treatment, @Param("amount")Double paymentAmount);

    @Select("SELECT mc.id, mc.number,\n" +
            "                mc.balance,\n" +
            "                mc.user,\n" +
            "                mc.level,\n" +
            "                mc.project,\n" +
            "                mc.due_date dueDate,\n" +
            "                mc.discount,\n" +
            "                mc.amount amount,\n" +
            "                mc.given_amount givenAmount,\n" +
            "                mc.create_time createTime\n" +
            "            FROM\n" +
            "                membership_card mc\n" +
            "            LEFT JOIN member m ON m.id = mc.user\n" +
            "            WHERE m.clinic = #{depId} ORDER BY mc.create_time desc")
    List<MembershipCard> selectThisDept(Integer depId);

    List<Map<String,Object>> selectAllCard();

    List<Map<String,Object>> selectSuggestCardListByDep(@Param("key") String key, @Param("dep") String dep);

    List<Map<String,Object>> selectAllCardByDep(@Param("dep") String dep);
}
