package com.itheima.sys.auth.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * auth通用工具
 * @author 10445
 */
public class AuthToolsFunc {

    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
