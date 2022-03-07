package com.itheima.shopcommon.entitys.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author 10445
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("shop_order_preferential")
public class ShopOrderPreferentialEntity implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long orderPreferentialId;
    /**
     * 订单id
     */
    @TableField(value = "o_id")
    private Long orderId;
    /**
     * 优惠编码
     */
    @TableField(value = "tial_code")
    private String tialCode;
}
