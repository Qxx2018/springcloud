package com.itheima.shoporder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
@SpringBootTest
public class NacosTest {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Test
    public void test01() {
        System.out.println();
    }

}
