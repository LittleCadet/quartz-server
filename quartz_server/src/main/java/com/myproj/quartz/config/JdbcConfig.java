package com.myproj.quartz.config;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    public DataSource pinpointDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://10.0.0.53:3306/kkl_scheduler?characterEncoding=UTF-8");
        dataSource.setUsername("scheduler_user");
        dataSource.setPassword("abc123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        dataSource.setMaxActive(10);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(20000);
        dataSource.setMinIdle(1);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);

        dataSource.setTimeBetweenConnectErrorMillis(30000);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");

        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(50);

        return dataSource;
    }
}
