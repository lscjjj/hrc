package com.cebrains.hrc.generator.action.config;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 默认的代码生成的配置
 *
 * @author frank
 * @date 2017-10-28-下午8:27
 */
public class HRCGeneratorConfig extends AbstractGeneratorConfig {

    protected void globalConfig() {
        globalConfig.setOutputDir("D:\\ideaSpace\\hrc\\hrc-admin\\src\\main\\java");//写自己项目的绝对路径,注意具体到java目录
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        globalConfig.setAuthor("frank");
    }

    protected void dataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("hrcu");
        dataSourceConfig.setPassword("go_range_1_2_3_4_5_6");
        dataSourceConfig.setUrl("jdbc:mysql://39.104.81.209:3306/adi_user_center?characterEncoding=utf8");
    }

    protected void strategyConfig() {
        //strategyConfig.setTablePrefix(new String[]{"xx_"});// 此处可以修改为您的表前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
    }

    protected void packageConfig() {
        packageConfig.setParent(null);
        packageConfig.setEntity("com.cebrains.hrc.admin.common.persistence.model");
        packageConfig.setMapper("com.cebrains.hrc.admin.common.persistence.dao");
        packageConfig.setXml("com.cebrains.hrc.admin.common.persistence.dao.mapping");
    }

    protected void contextConfig() {
        contextConfig.setProPackage("com.cebrains.hrc.admin");
        contextConfig.setCoreBasePackage("com.cebrains.hrc.core");
        contextConfig.setBizChName("字典管理");
        contextConfig.setBizEnName("sysDict");
        contextConfig.setModuleName("system");
        contextConfig.setProjectPath("D:\\ideaSpace\\hrc\\hrc-admin");//写自己项目的绝对路径
        contextConfig.setEntityName("SysDict");
        sqlConfig.setParentMenuName(null);//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(true);
        contextConfig.setDaoSwitch(true);
        contextConfig.setServiceSwitch(true);

        /**
         * hrc 生成器开关
         */
        contextConfig.setControllerSwitch(true);
        contextConfig.setIndexPageSwitch(true);
        contextConfig.setAddPageSwitch(true);
        contextConfig.setEditPageSwitch(true);
        contextConfig.setJsSwitch(true);
        contextConfig.setInfoJsSwitch(true);
        contextConfig.setSqlSwitch(true);
    }

    @Override
    protected void config() {
        globalConfig();
        dataSourceConfig();
        strategyConfig();
        packageConfig();
        contextConfig();
    }
}
