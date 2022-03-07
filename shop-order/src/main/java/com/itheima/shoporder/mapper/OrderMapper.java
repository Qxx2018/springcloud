package com.itheima.shoporder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.shopcommon.entitys.order.ShopOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<ShopOrderEntity> {
}
