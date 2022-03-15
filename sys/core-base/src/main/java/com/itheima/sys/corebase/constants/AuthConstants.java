package com.itheima.sys.corebase.constants;

/**
 * auth常量
 * @author 10445
 */
public class AuthConstants {

    /**
     * cache key前缀
     * 避免项目间key冲突
     */
    public static final String CACHEKEY = "SC";
    /**
     * redis存储资源权限key
     */
    public static final String SYSAUTHREDISKEY = "Auth-Resource";
    /**
     * 未登入用户 默认的权限
     */
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    /**
     * 自定义 未登入用户权限
     */
    public static final String ROLE_LOGIN = "LOGIN";


}
