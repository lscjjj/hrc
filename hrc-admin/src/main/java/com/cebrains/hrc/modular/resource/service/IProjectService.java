package com.cebrains.hrc.modular.resource.service;

import com.cebrains.hrc.common.persistence.model.Project;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author frank123
 * @since 2018-03-06
 */
public interface IProjectService extends IService<Project> {

    List<Project> projectInformationByTreatment(Integer treatment);

    List<Project> selectPrice(Integer id);

    List<Project> selectOne();

    List<Project> selectTwo();

    List<Project> selectThree();

    List<Project> selectFour();

    List<Project> selectFive();
}
