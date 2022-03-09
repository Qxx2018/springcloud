package com.itheima.sys.auth.service;

import java.util.Map;

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

    /**
     * 从redis读取权限
     * @param key
     * @return
     */
    Map<String, String> readAuth(String key);

}
