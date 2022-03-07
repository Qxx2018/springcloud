package com.itheima.shopcommon.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情Vo
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 商品单位
     */
    private String productUnit;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 商品库存
     */
    private Integer productStock;
}
