package com.itheima.gateway.web.router;


import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
/**
 * 动态路由实现类
 * @author 10445
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
    /**
     * RouteDefinitionWriter
     * 封装了对路由定义信息的获取、增加、删除操作
     */
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    /**
     * RouteDefinitionLocator
     * 路由定义定位器接口
     * 只有一个方法getRouteDefinitions，用来获取路由定义列表的方法。
     */
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 发布事件
     */
    @Autowired
    private ApplicationEventPublisher publisher;



    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 删除路由
     * @param id
     */
    public void delete(String id) {
        try {
            log.info("gateway delete route id : {}",id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        }
        catch (Exception e) {
            log.error("gateway delete route id error : {}",e.toString());
        }
    }

    /**
     * 更新路由
     */
    public void updateList(List<RouteDefinition> definitionList) {
        log.info("gateway update route {}", definitionList);
        //删除缓存routerDefinition
        List<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitions)) {
            routeDefinitions.forEach(routeDefinition -> {
                log.info("delete routeDefinition:{}", routeDefinition);
                delete(routeDefinition.getId());
            });
        }
        definitionList.forEach(this::updateById);
    }
    /**
     * 更新路由
     */
    public void updateById(RouteDefinition routeDefinition) {
        try {
            log.info("gateway update route {}", routeDefinition);
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        }
        catch (Exception e) {
            routeDefinition.getId();
            return;
        }
        try {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        }
        catch (Exception e) {

        }
    }
    /**
     * 增加路由
     */
    public String add(RouteDefinition routeDefinition) {
        log.info("gateway add route {}",routeDefinition);
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }


}
