package com.cebrains.hrc.generator.action;


import com.cebrains.hrc.generator.action.config.HRCGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 * @author frank
 * @Date 2017/5/21 12:38
 */
public class HRCCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        HRCGeneratorConfig hrcGeneratorConfig = new HRCGeneratorConfig();
        hrcGeneratorConfig.doMpGeneration();

        /**
         * hrc的生成器:
         *      hrc的代码生成器可以生成controller,html页面,页面对应的js
         */
        hrcGeneratorConfig.doHRCGeneration();
    }

}