package com.biocar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * @author DeSen Xu
 * @date 2021-11-11 14:25
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfigurationProperties {

    private boolean enabled;

    private String title;
    private String baseUrl;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    /**
     * 作者信息
     */
    private Contact contact;
    private String license;
    private String licenseUrl;
}
