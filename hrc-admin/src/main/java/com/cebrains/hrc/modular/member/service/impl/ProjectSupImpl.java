package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.ProjectSupMapper;
import com.cebrains.hrc.common.persistence.model.ProjectSup;
import com.cebrains.hrc.modular.member.service.IProjectSupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 康复项目父节点表 Mapper 服务实现类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
@Service
public class ProjectSupImpl extends ServiceImpl<ProjectSupMapper, ProjectSup> implements IProjectSupService {

    @Autowired
    private ProjectSupMapper projectSupMapper;

    @Override
    public List<ProjectSup> selectList() {
        return projectSupMapper.selectList();
    }
}
