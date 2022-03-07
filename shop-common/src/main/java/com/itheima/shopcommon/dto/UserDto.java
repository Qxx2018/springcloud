package com.itheima.shopcommon.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增用户dto
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String userName;
    @NotNull(message="用户手机号不为空")
    @Pattern(regexp = "^1[0-9]{10}$",message = "用户手机号格式不对")
    private String userPhone;
}
