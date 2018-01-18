/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon;

import com.dragon.task.MyJobFactory;
import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * -*--------------------kkk--------------------
 * -*------------------kkk-kkk------------------
 * -*-------------kkkkkkk----kkkkkk-------------
 * -*-------------kkkk---------kkkk-------------
 * -*-------------k-----kkkkkkk---k-------------
 * -*-------------k--kkkkkkkkk----k-------------
 * -*-------------k-kkkkkkkkkkk---k-------------
 * -*-------------kkkkkkkkkkkkkk--kk-------------
 * -*-kk----------kk-kkkkkkkkkk--kk----------kk-
 * -*--kkkkkkkkkkkkk-kkkk----kk--kkkkkkkkkkkkk--
 * -*------------kkk-kkk----kkk--kkk------------
 * -*---kkkkkkkkk--kk------kkkk-kk--kkkkkkkkk---
 * -*----kkkkkkkk--kk---kkkkkk--kk--kkkkkkkk----
 * -*----k---------kkkkkkkkkk--kkk---------k----
 * -*-----kkkk-kk--kkkkkkkkk---kkk--kk-kkkk-----
 * -*-----k----kk-kkkkkkk-----kbkkk-kk----------
 * -*------kkk-kkkkk--kkk---kkk--kkkkk-kkk------
 * -*-------kk-kkk------kkkkk------kkk-kk------- -*
 * -*-----------------------_oo0oo_------------------------------------------------
 * -*----------------------o8888888o-----------------------------------------------
 * -*----------------------88"-.-"88-----------------------------------------------
 * -*----------------------(|--_--|)-----------------------------------------------
 * -*----------------------0\--=--/0-----------------------------------------------
 * -*--------------------___/`---'\___---------------------------------------------
 * -*------------------.'-\\|-----|//-'.-------------------------------------------
 * -*-----------------/-\\|||--:--|||//-\------------------------------------------
 * -*----------------/-_|||||--:--|||||--\-----------------------------------------
 * -*---------------|---|-\\\-----///-|---|----------------------------------------
 * -*---------------|-\_|--''\---/''--|_/-|----------------------------------------
 * -*---------------\--.-\__--'-'--___/-.-/----------------------------------------
 * -*-------------___'.-.'--/--.--\--`.-.'___--------------------------------------
 * -*----------.""-'<--`.___\_<|>_/___.'->'-"".------------------------------------
 * -*---------|-|-:--`--\`.;`\-_-/`;.`/---`-:-|-|----------------------------------
 * -*---------\--\-`_.---\_-__\-/__-_/---.-`-/--/----------------------------------
 * -*-----=====`-.____`.___-\_____/___.-`___.-'=====-------------------------------
 * -*------------------------------------------------------------------------------
 * -*@author Charles Dong <charles@xingbod.cn>
 */
@Configuration
@EnableScheduling
public class QuartzSchedule {

    @Autowired
    private MyJobFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);

        // 延时启动
        factory.setStartupDelay(20);

        // 加载quartz数据源配置
        factory.setQuartzProperties(quartzProperties());

        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(myJobFactory);

        return factory;
    }

    /**
     * 加载quartz数据源配置
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
