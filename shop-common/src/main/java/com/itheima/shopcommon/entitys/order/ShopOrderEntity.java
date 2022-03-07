package com.itheima.shopcommon.entitys.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("shop_order")
public class ShopOrderEntity implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long orderId;
    @TableField(value = "u_id")
    private Long userId;
    @TableField(value = "p_code")
    private String productCode;
}
