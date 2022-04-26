package com.itheima.gateway.web.config;

import javax.annotation.Resource;

/**
 * 鉴权配置
 * @author 10445
 */
public class SecurityConfig {

    /**
     * 白名单
     * 无需鉴权
     */
    @Resource
    private WhiteListConfig whiteListConfig;




}
