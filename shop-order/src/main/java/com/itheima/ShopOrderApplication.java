package com.itheima;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.itheima"})
@MapperScan("com.itheima.shoporder.mapper")
@EnableDiscoveryClient(autoRegister = true)
@EnableFeignClients
//seata 开启数据库代理
//@EnableAutoDataSourceProxy
@EnableConfigurationProperties
public class ShopOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
        System.out.println("=======================ShopOrderApplication Srart ==================================");

    }
}
