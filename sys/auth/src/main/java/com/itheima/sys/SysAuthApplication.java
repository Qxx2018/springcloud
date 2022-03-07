package com.itheima.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 10445
 */
@SpringBootApplication(scanBasePackages = {"com.itheima.sys"})
@MapperScan("com.itheima.sys.auth.mapper")
@EnableDiscoveryClient(autoRegister = true)
@EnableFeignClients
@EnableConfigurationProperties
public class SysAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysAuthApplication.class, args);
        System.out.println("=======================SysAuthApplication Srart ==================================");
    }
}
