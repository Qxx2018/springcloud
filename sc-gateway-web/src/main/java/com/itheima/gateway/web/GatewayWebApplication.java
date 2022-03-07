package com.itheima.gateway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.itheima"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties
public class GatewayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
        System.out.println("=======================GatewayWebApplication Srart ==================================");
    }
    /**
     * nacos disconvery中默认已经引入了ribbon 不需要单独引入
     * 添加@LoadBalanced注解
     * 调用的时候使用服务器名称代替ip+端口 （即使用默认轮询的负载均衡方式）
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
