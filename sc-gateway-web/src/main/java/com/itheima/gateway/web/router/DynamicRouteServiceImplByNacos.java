package com.itheima.gateway.web.router;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 根据nacos动态配置路由
 * @author 10445
 */
@Component
@Slf4j
@DependsOn({"dynamicRouteConfig"})
public class DynamicRouteServiceImplByNacos {
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("gateway route init...");
        try {
            configService = initConfigService();
            if (configService == null) {
                log.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(
              DynamicRouteConfig.NACOS_ROUTE_DATA_ID,
              DynamicRouteConfig.NACOS_ROUTE_GROUP,
              DynamicRouteConfig.DEFAULT_TIMEOUT
            );
            log.info("获取网关当前配置:\r\n{}",configInfo);
            List<RouteDefinition> definitions = JSON.parseArray(
                    configInfo, RouteDefinition.class
            );
            for (RouteDefinition definition : definitions) {
                log.info("update route : {}",definition.toString());
                dynamicRouteService.add(definition);
            }
        }
        catch (Exception e) {
            log.error("初始化网关路由发生错误",e);
        }
        dynamicRouteByNacosListener(DynamicRouteConfig.NACOS_ROUTE_DATA_ID,DynamicRouteConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * 初始化网关路由
     */
    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr",DynamicRouteConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace",DynamicRouteConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);

        }
        catch (Exception e) {
            log.error("初始化网关路由时发生错误",e);
            return null;
        }
    }

    /**
     * 监听Nacos下发生的动态路由配置
     *
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(
                    dataId, group, new Listener() {
                        @Override
                        public Executor getExecutor() {
                            log.info("getExecutor\n\r");
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            log.info("进行网关更新:\n\r{}",configInfo);
                            List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                            log.info("update route : {}", routeDefinitions.toString());
                            dynamicRouteService.updateList(routeDefinitions);
                        }
                    }
            );
        }
        catch (NacosException e) {
            log.error("从nacos接收动态路由配置出错！",e);
        }
    }

    /**
     * 根据nacos配置获取路由
     */
    public List<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> definitions = new ArrayList<>();
        try {
            //本地路由为空，从nacos取值
            String content = configService.getConfig(
                    DynamicRouteConfig.NACOS_ROUTE_DATA_ID,
                    DynamicRouteConfig.NACOS_ROUTE_GROUP,
                    DynamicRouteConfig.DEFAULT_TIMEOUT);
            if (StrUtil.isNotBlank(content)) {
                definitions = JSON.parseArray(content,RouteDefinition.class);
            }
            return definitions;
        } catch (NacosException e) {
            log.error("获取动态路由失败",e);
        }
        return definitions;
    }
}
