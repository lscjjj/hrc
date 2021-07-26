package com.cebrains.hrc.common.persistence.dao;

import com.cebrains.hrc.common.persistence.model.Consumable;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.MemberFamily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 耗材管理 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-05-10
 */
@Mapper
public interface ConsumableMapper extends BaseMapper<Consumable> {

    List<Consumable> selectByDeptId(Integer deptid);

}
