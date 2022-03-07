package com.itheima.shopcommon.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户详情Vo
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = -762390872735679112L;

    private Long userId;

    private String userName;

    private String userPhone;

    private BigDecimal userBalance;
}
