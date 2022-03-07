package com.itheima.shopcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单优惠传入
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferentialDto implements Serializable {
    /**
     * 执行优惠的订单号
     */
    private String orderId;
    /**
     * 优惠编号
     */
    private String tialCode;
}
