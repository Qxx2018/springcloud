package com.itheima.shopcommon.entitys.user;

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
@TableName(value = "shop_user")
public class ShopUserEntity implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long userId;
    @TableField("u_name")
    private String userName;
    @TableField("u_phone")
    private String userPhone;
    @TableField("u_balance")
    private BigDecimal userBalance;
}
