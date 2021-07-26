package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.History;

import java.util.List;

/**
 * <p>
 * 会员修改历史表 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface HistoryMapper extends BaseMapper<History> {

    List<History> selectList(Integer id);
}
