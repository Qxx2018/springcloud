package com.itheima.sys.coredata.dto.base.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * jwt 负载payload
 * @author 10445
 */
@Data
public class PayLoadDo implements Serializable {

    private static final long serialVersionUID = -2280775598410101314L;

    /**
     * 账户号
     */
    private String username;

    /**
     * 登入密码
     */
    private String password;

    /**
     * 角色编码
     */
    private List<String> roleCodeList;

    /**
     * 资源权限
     */
    private List<String> resourceCodeList;
}
