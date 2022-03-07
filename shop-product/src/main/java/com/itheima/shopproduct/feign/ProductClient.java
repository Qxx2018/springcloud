//package com.itheima.shopproduct.feign;
//
//import com.itheima.api.fegin.ProductApi;
//import com.itheima.shopproduct.service.ProductService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 服务间调用api实现类
// */
//@RestController
//@Slf4j
//@AllArgsConstructor
//public class ProductClient implements ProductApi {
//    @Autowired
//    private ProductService productService;
//
//    /**
//     * 获取产品详情
//     *
//     * @param pid
//     * @return
//     */
//    @Override
//    public String searchProductDetail(Integer pid) {
//        return productService.test(pid);
//    }
//}
