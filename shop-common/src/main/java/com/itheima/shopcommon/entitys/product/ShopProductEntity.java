package com.itheima.shopcommon.entitys.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("shop_product")
public class ShopProductEntity implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;

    @TableField(value = "p_name")
    private String productName;

    @TableField(value = "p_price")
    private BigDecimal productPrice;

    @TableField(value = "p_unit")
    private String productUnit;

    @TableField(value = "p_code")
    private String productCode;

    @TableField(value = "stock")
    private Integer productStock;
}
