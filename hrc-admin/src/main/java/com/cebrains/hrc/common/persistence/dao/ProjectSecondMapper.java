package com.cebrains.hrc.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cebrains.hrc.common.persistence.model.ProjectSecond;
import com.cebrains.hrc.common.persistence.model.ProjectSup;

import java.util.List;

/**
 * <p>
 * 康复项目父节点表 Mapper 接口
 * </p>
 *
 * @author frank123
 * @since 2018-03-08
 */
public interface ProjectSecondMapper extends BaseMapper<ProjectSecond> {
    List<ProjectSecond> selectList();
}
