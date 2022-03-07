package com.itheima;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.itheima"})
@MapperScan("com.itheima.shopuser.mapper")
@EnableDiscoveryClient(autoRegister = true)
@EnableFeignClients
@EnableConfigurationProperties
public class ShopUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopUserApplication.class, args);
        System.out.println("=======================ShopUserApplication Srart ==================================");
    }
}
