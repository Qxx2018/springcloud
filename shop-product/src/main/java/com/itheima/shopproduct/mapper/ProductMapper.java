package com.itheima.shopproduct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<ShopProductEntity> {
}
