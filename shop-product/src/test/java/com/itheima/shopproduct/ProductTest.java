package com.itheima.shopproduct;

import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import com.itheima.shopproduct.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.Resource;
@SpringBootTest
public class ProductTest {
    @Resource
    private ProductService productService;
    @Test
    public void productOne() {
        System.out.println();
    }
}
