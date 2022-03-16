package com.itheima.sys.coredata.dto.base.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * jwt 负载payload
 * @author 10445
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayLoadDo implements Serializable {

    private static final long serialVersionUID = -2280775598410101314L;

    /**
     * 账户号
     * 对应token的键username
     */
    private String username;

    /**
     * 登入密码
     * 对应token的键password
     */
    private String password;

    /**
     * 角色编码
     * 对应token的键roleCode
     */
    private List<String> roleCodeList;

    /**
     * 资源权限
     * 对应token的键resourceCode
     */
    private List<String> resourceCodeList;
}
