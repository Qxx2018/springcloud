package com.itheima.sys.auth.service;

import com.itheima.sys.auth.dto.AuthPermissionDo;

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

    /**
     * 用户登入后根据用户账户账号获取用户账号信息，角色，资源权限
     * @param username 登入账号
     * @return
     */
    AuthPermissionDo authPermissionByUserName(String username);

    /**
     * 用户登入后根据用户账户账号生成jwt-token
     * @param username 登入账号
     * @return
     */
    String createJwtTokenAfterLogin(String username);
}
