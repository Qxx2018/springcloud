package com.itheima.sys.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AuthTest {

    /**
     * 生成虚拟的JWT token
     */
    @Test
    public void sign() {
        String tokenSecret = "sc-auth-1044582360";
        //私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);

        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type","JWT");
        header.put("alg","HS256");

        //设置过期时间
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.DATE, 15);
        Date time = cd.getTime();

        //payload数据
//        HashMap<String, String> map = new HashMap<>();
//        map.put("accountNumber","1044582360");
//        map.put("accountPassword","1044582360");
//        map.put("roleCode","ADMIN");
//        map.put("resourceCode","sys:admin:menu:view");

        String token =
                JWT.create()
                .withHeader(header)
                .withClaim("accountNumber","1044582360")
                .withClaim("accountPassword", "1044582360")
                .withClaim("roleCode","ADMIN")
                .withClaim("resourceCode","sys:admin:menu:view")
                .withExpiresAt(time)
                .sign(algorithm);
        System.out.println("token=>"+token);
    }

}
