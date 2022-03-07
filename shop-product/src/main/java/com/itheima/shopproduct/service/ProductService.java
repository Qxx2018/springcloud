package com.itheima.shopproduct.service;

import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import com.itheima.shopcommon.exceptions.ProductException;
import com.itheima.shopcommon.service.BaseService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface ProductService extends BaseService<ShopProductEntity> {



    /**
     * 查询商品
     * @param code 商品编码
     * @return ShopProductEntity
     */
    ShopProductEntity productOne(String code);

    /**
     * 扣库存
     * @param code 商品编码
     * @param num 扣除数量
     * @throws ProductException
     * @return
     */
    Boolean deduct(String code, Integer num) throws ProductException;

}
