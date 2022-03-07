package com.itheima.shoporder.service;

import com.itheima.shopcommon.dto.PreferentialDto;
import com.itheima.shopcommon.entitys.order.ShopOrderPreferentialEntity;
import com.itheima.shopcommon.exceptions.OrderException;
import com.itheima.shopcommon.service.BaseService;

public interface ShopOrderPreferentialService extends BaseService<ShopOrderPreferentialEntity> {
    /**
     * 创建订单优惠记录
     * @param dto 订单优惠传入
     * @return
     */
    ShopOrderPreferentialEntity createPreferentialRecord(ShopOrderPreferentialEntity dto) throws OrderException;
}
