package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.HistoryMapper;
import com.cebrains.hrc.common.persistence.model.History;
import com.cebrains.hrc.common.persistence.model.Member;
import com.cebrains.hrc.modular.member.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 修改会员历史表 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public List<History> selectList(Integer id) {
        return historyMapper.selectList(id);
    }
}
