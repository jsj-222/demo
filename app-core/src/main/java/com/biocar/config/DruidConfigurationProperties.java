package com.biocar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2021-11-12 15:59
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfigurationProperties {

    private String url;

    private String username;

    private String password;

    /**
     * 初始化时建立物理连接的个数
     */
    private int initialSize;

    /**
     * 最小连接池数量
     */
    private int minIdle = 4;

    /**
     * 最大连接池数量
     */
    private int maxActive = 8;

    /**
     * 当无法连接数据库时，最大重试次数
     */
    private int maxRetryTimes = 2;

    /**
     * 每次重试的间隔时间
     */
    private long retryDuration = 1000 * 60;
}
