package com.itheima.api.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 服务间调用：商品接口
 * @author 10445
 */
@Component
@FeignClient(name = "shop-product-service", url = "http://192.168.237.1:8181")
public interface ProductApi {

    /**
     * 扣减商品库存
     * @param stock 扣除的商品数
     * @param code 商品编码
     */
    @GetMapping(value = "/sp/deduct/{code}")
    Boolean deduct(@PathVariable("code") String code, @RequestParam(value = "stock", required = true) Integer stock);

}
