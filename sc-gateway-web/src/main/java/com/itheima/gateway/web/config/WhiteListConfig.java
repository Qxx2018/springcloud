package com.itheima.gateway.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 鉴权白名单
 * @author 10445
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sc.whitelist")
public class WhiteListConfig {
    private List<String> urls;
}
