package com.itheima.shopproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import com.itheima.shopcommon.exceptions.ProductException;
import com.itheima.shopproduct.mapper.ProductMapper;
import com.itheima.shopproduct.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Wrapper;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ShopProductEntity> implements ProductService {


    /**
     * 查询商品
     *
     * @param code 商品编码
     * @return ShopProductEntity
     */
    @Override
    public ShopProductEntity productOne(String code) {
        LambdaQueryWrapper<ShopProductEntity> queryWrapper = Wrappers.<ShopProductEntity>lambdaQuery().eq(ShopProductEntity::getProductCode,code);
        ShopProductEntity productEntity = this.getOne(queryWrapper);
        return productEntity;
    }


    /**
     * 扣库存
     *
     * @param code 商品编码
     * @param num  扣除数量
     * @return
     */
    @Override
    @Transactional(rollbackFor = ProductException.class)
    public Boolean deduct(String code, Integer num) throws ProductException {
        ShopProductEntity productEntity = this.productOne(code);
        Integer stock = productEntity.getProductStock();
        if (stock < num) {
            throw new ProductException("商品:"+code+"库存不足");
        }
        Integer newStock = stock - num;
        LambdaUpdateWrapper<ShopProductEntity> updateWrapper = Wrappers.<ShopProductEntity>lambdaUpdate()
                .set(ShopProductEntity::getProductStock,newStock).eq(ShopProductEntity::getProductCode,code);
        this.update(updateWrapper);
        return Boolean.TRUE;
    }
}
