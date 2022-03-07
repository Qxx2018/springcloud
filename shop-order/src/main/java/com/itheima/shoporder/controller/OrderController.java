package com.itheima.shoporder.controller;

import com.itheima.api.fegin.ProductApi;
import com.itheima.shopcommon.dto.PlaceOrderDto;
import com.itheima.shopcommon.dto.PreferentialDto;
import com.itheima.shopcommon.entitys.order.ShopOrderPreferentialEntity;
import com.itheima.shopcommon.exceptions.OrderException;
import com.itheima.shoporder.service.OrderService;
import com.itheima.shoporder.service.ShopOrderPreferentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/so")
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 订单优惠服务
     */
    @Autowired
    private ShopOrderPreferentialService orderPreferentialService;

    /**
     * 下单
     * @param placeOrderDto
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public void placeOrder(@RequestBody(required = true) PlaceOrderDto placeOrderDto ) throws Exception {
        orderService.placeOrder(placeOrderDto);
    }
    /**
     * 配置详情
     * @return
     */
    @RequestMapping(value = "/order/key",method = RequestMethod.GET)
    public String key(){
        return orderService.config();
    }
    /**
     * 创建订单优惠记录
     */
    @RequestMapping(value = "/order/preferential",method = RequestMethod.POST)
    public void preferentialOrder(@RequestBody(required = true) PreferentialDto dto) throws OrderException {

        orderPreferentialService.createPreferentialRecord(
                ShopOrderPreferentialEntity.builder()
                        .orderId(Long.parseLong(dto.getOrderId()))
                        .tialCode(dto.getTialCode()).build()

        );
    }
}
