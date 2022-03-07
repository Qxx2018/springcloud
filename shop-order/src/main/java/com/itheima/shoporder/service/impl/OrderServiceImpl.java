package com.itheima.shoporder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.api.fegin.ProductApi;
import com.itheima.api.fegin.UserApi;
import com.itheima.shopcommon.dto.DebitBalanceDto;
import com.itheima.shopcommon.dto.PlaceOrderDto;
import com.itheima.shopcommon.entitys.order.ShopOrderEntity;
import com.itheima.shopcommon.exceptions.OrderException;
import com.itheima.shoporder.mapper.OrderMapper;
import com.itheima.shoporder.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * RefreshScope配置动态刷新
 */
@Service
@Slf4j
@RefreshScope
public class OrderServiceImpl extends ServiceImpl<OrderMapper, ShopOrderEntity> implements OrderService {
    @Value("${shop.order.key}")
    private String key;
    @Value("${sc.url}")
    private String url;

    @Autowired
    private ProductApi productApi;
    @Autowired
    private UserApi userApi;

    @Override
    public String test() {
        return "Hello Order!";
    }

    @Override
    public String config() {
        return key+url;
    }

    /**
     * 下单：创建订单-扣除库存-扣除用户余额
     *
     * @param dto
     */
    @Override
    @GlobalTransactional(name = "place-order-t", rollbackFor = Exception.class)
    public Boolean placeOrder(PlaceOrderDto dto) throws Exception {

        ShopOrderEntity shopOrderEntity = ShopOrderEntity.builder()
                .userId(Long.parseLong(dto.getUserId()))
                .productCode(dto.getProductCode())
                .build();
        //创建订单
        this.createOrder(shopOrderEntity);
        //扣除库存
        productApi.deduct(dto.getProductCode(),Integer.parseInt(dto.getProductStock()));
        //扣减用户账户余额
        DebitBalanceDto debitBalanceDto = DebitBalanceDto.builder()
                .userId(dto.getUserId())
                .balance(dto.getPrice()).build();
        userApi.debit(debitBalanceDto);
        return Boolean.TRUE;
    }

    /**
     * 创建订单
     *
     * @return
     */
    @Override
    public ShopOrderEntity createOrder(ShopOrderEntity orderEntity) {
        super.baseMapper.insert(orderEntity);
        return orderEntity;
    }
}
