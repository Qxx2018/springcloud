package com.itheima.shopcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 创建订单的传入参数类
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDto implements Serializable {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品数量
     */
    private String productStock;
    /**
     * 商品价格
     */
    private String price;

}
