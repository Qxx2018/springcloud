package com.itheima.sys.coredata.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登入账户请求
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysAccountReqDto implements Serializable {

    private static final long serialVersionUID = -3150954474277635438L;

    /**
     * 登入账号
     */
    @NotBlank(message = "登入账号不为空")
    @Length(min = 8, max = 16, message = "长度在8到16位之间")
    private String accountNumber;

    /**
     * 登入密码
     */
    @NotBlank(message = "登入密码不为空")
    @Length(min = 8, max = 16, message = "密码长度在8到16位之间")
    private String accountPassword;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;
}
