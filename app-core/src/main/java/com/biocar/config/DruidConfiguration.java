package com.biocar.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DeSen Xu
 * @date 2021-11-12 15:58
 */
@Configuration
@EnableConfigurationProperties(DruidConfigurationProperties.class)
public class DruidConfiguration {

    private DruidConfigurationProperties druidConfigurationProperties;

    @Autowired
    public void setDruidConfigurationProperties(DruidConfigurationProperties druidConfigurationProperties) {
        this.druidConfigurationProperties = druidConfigurationProperties;
    }

    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setUrl(druidConfigurationProperties.getUrl());
        druidDataSource.setUsername(druidConfigurationProperties.getUsername());
        druidDataSource.setPassword(druidConfigurationProperties.getPassword());
        druidDataSource.setConnectionErrorRetryAttempts(druidConfigurationProperties.getMaxRetryTimes());
        druidDataSource.setTimeBetweenConnectErrorMillis(druidConfigurationProperties.getRetryDuration());
        druidDataSource.setMinIdle(druidConfigurationProperties.getMinIdle());
        druidDataSource.setMaxActive(druidConfigurationProperties.getMaxActive());
        druidDataSource.setInitialSize(druidConfigurationProperties.getInitialSize());
        druidDataSource.setMaxWait(2000);

        return druidDataSource;
    }


}
