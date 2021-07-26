package com.cebrains.hrc.modular.system.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cebrains.hrc.common.persistence.model.MembershipCard;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员卡 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-04-16
 */
public interface IMembershipCardService extends IService<MembershipCard> {

    List<Map<String,Object>> selectSuggestCardList(String key);

    void deductMoney(Integer treatment, Double paymentAmount);

    void deductMoneyByMemberShipCard(Integer id,Double paymentAmount);

    void deductMoneyByTransferCardConsumption(String treatment, Double paymentAmount);

    void moneyByTransferCardConsumption(String treatment, Double paymentAmount);

    List<MembershipCard> selectThisDept(Integer depId);

    List<Map<String,Object>> selectAllCard();

    List<Map<String,Object>> selectSuggestCardListByDep(String key, String dep);

    List<Map<String,Object>> selectAllCardByDep(String dep);
}
