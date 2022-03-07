package com.itheima.gateway.web.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由网关配置
 * 如和对应nacos配置中心的文件gateway-router.json
 * @author 10445
 */
@Configuration
public class DynamicRouteConfig {

    /**
     * 超市时间
     */
    public static final long DEFAULT_TIMEOUT = 30000;

    /**
     * nacos服务地址
     */
    public static String NACOS_SERVER_ADDR;
    /**
     * nacos服务命名空间
     */
    public static String NACOS_NAMESPACE;

    /**
     * nacos配置中心里动态路由配置
     */
    public static String NACOS_ROUTE_DATA_ID;

    /**
     * nacos组
     */
    public static String NACOS_ROUTE_GROUP;

    @Value("${sc.nacos.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }
    @Value("${sc.nacos.namespace}")
    public void setNacosNamespace(String nacosNamespace) {
        NACOS_NAMESPACE = nacosNamespace;
    }
    @Value("${sc.gateway.dynamic-route.data-id}")
    public void setNacosRouteDataId(String nacosRouteDataId) {
        NACOS_ROUTE_DATA_ID = nacosRouteDataId;
    }
    @Value("${sc.gateway.dynamic-route.group}")
    public void setNacosRouteGroup(String nacosRouteGroup) {
        NACOS_ROUTE_GROUP = nacosRouteGroup;
    }

}
