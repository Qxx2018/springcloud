package com.itheima.shoporder.service;

import com.itheima.shopcommon.dto.PlaceOrderDto;
import com.itheima.shopcommon.entitys.order.ShopOrderEntity;
import com.itheima.shopcommon.exceptions.OrderException;
import com.itheima.shopcommon.service.BaseService;

public interface OrderService extends BaseService<ShopOrderEntity> {
    String test();
    String config();
    /**
     * 下单：创建订单-扣除库存-扣除用户余额
     */
    Boolean placeOrder(PlaceOrderDto dto) throws Exception;
    /**
     * 创建订单
     * @return
     */
    ShopOrderEntity createOrder(ShopOrderEntity orderEntity);
}
