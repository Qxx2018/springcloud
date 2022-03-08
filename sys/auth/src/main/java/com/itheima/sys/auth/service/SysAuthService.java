package com.itheima.sys.auth.service;

/**
 * 权限服务
 * @author 10445
 */
public interface SysAuthService {

    /**
     * 往redis存储权限
     * @return
     */
    boolean storeAuth();

}
