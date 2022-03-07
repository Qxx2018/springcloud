package com.itheima.shopcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 扣款dto
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitBalanceDto implements Serializable {
    @NotEmpty(message = "用户id不为空")
    private String userId;
    @NotEmpty(message = "扣款金额不为空")
    @Pattern(regexp = "(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0(\\.\\d{1,2})?$)",message = "扣款金额格式不对")
    private String balance;

}
