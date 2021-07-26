package com.cebrains.hrc.modular.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.cebrains.hrc.common.persistence.model.ProjectSecond;
import com.cebrains.hrc.common.persistence.model.ProjectSup;

import java.util.List;

/**
 * <p>
 * 康复项目父节点表 Mapper 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface IProjectSecondService extends IService<ProjectSecond> {
    List<ProjectSecond> selectList();
}
