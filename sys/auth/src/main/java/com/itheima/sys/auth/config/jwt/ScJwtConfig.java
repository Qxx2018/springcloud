package com.itheima.sys.auth.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt 配置
 * @author 10445
 */
@Data
@Component
@ConfigurationProperties("sc.jwt.config")
public class ScJwtConfig {

    private String headerType;

    private String headerAlg;

    private String tokenSecret;

    private Integer durationDayTime;


}
