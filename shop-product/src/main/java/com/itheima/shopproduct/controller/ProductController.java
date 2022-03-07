package com.itheima.shopproduct.controller;

import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import com.itheima.shopcommon.exceptions.ProductException;
import com.itheima.shopcommon.vo.ProductVo;
import com.itheima.shopproduct.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/sp")
public class ProductController {
    @Autowired
    private ProductService productService;


    /**
     * 查询商品详情
     * @param code 商品编码
     * @return ProductVo
     */
    @GetMapping(value = "/detail/{code}")
    public ProductVo productOne(@PathVariable("code") String code) {
        ShopProductEntity productEntity = productService.productOne(code);
        ProductVo productVo = ProductVo.builder()
                .productCode(productEntity.getProductCode())
                .productName(productEntity.getProductName())
                .productPrice( productEntity.getProductPrice())
                .productStock(productEntity.getProductStock())
                .build();
        return productVo;
    }
    /**
     * 扣减商品库存
     * @param code 商品编码
     * @param stock 扣除的商品数
     * @return
     */
    @GetMapping(value = "/deduct/{code}")
    public Boolean deduct(@PathVariable("code") String code, @RequestParam(value = "stock", required = true) Integer stock) throws ProductException {
        productService.deduct(code,stock);
        return Boolean.TRUE;
    }
}
