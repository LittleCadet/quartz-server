package com.myproj.quartz.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    // 配置文件路径
    private static final String QUARTZ_CONFIG = "/quartz.properties";

    /**
     * 从quartz.properties文件中读取Quartz配置属性
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_CONFIG));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource pinpointDataSource) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 设置自行启动
        factory.setAutoStartup(true);
        factory.setQuartzProperties(quartzProperties());
        factory.setDataSource(pinpointDataSource);
        factory.setJobFactory(new SimpleJobFactory());
        factory.setStartupDelay(10);

        return factory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }

}
