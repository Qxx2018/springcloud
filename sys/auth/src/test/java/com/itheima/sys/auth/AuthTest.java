package com.itheima.sys.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

@SpringBootTest
public class AuthTest {
    public static final String TOKEN_SECRET = "sc-auth-1044582360";
    /**
     * 生成虚拟的JWT token
     */
    @Test
    public void sign() {
        //私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type","JWT");
        header.put("alg","HS256");

        //设置过期时间
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.SECOND, 5);
        Date time = cd.getTime();
        String token =
                JWT.create()
                .withHeader(header)
                .withClaim("username","1044582360")
                .withClaim("password", "1044582360")
                .withArrayClaim("roleCode", new String[]{"ADMIN","ADMIN"})
                .withArrayClaim("resourceCode",new String[]{"sys:admin:menu:view","sys:admin:menu:view"})
                .withExpiresAt(time)
                .sign(algorithm);
        System.out.println("token=>"+token);
    }

    /**
     * 验证token
     */
    @Test
    public void verify() {
        String token = "eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKV1QiLCJ0eXAiOiJKV1QifQ.eyJwYXNzd29yZCI6IjEwNDQ1ODIzNjAiLCJyZXNvdXJjZUNvZGUiOlsic3lzOmFkbWluOm1lbnU6dmlldyIsInN5czphZG1pbjptZW51OnZpZXciXSwicm9sZUNvZGUiOlsiQURNSU4iLCJBRE1JTiJdLCJleHAiOjE2NDc0MjI1NDQsInVzZXJuYW1lIjoiMTA0NDU4MjM2MCJ9.MDru-G4D9HAnKsbwQLWg7jpF1vvfXtfDH0tT9XOiYSc";
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //未验证通过会抛出异常
            verifier.verify(token);
            System.out.println("通过");
        } catch (Exception e) {
            System.out.println("验证token未通过:"+e.toString());
        }
    }
    /**
     * 从token中获取数据 例如资源权限
     */
    @Test
    public void getResourceCode() {
        String toke = "eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKV1QiLCJ0eXAiOiJKV1QifQ.eyJwYXNzd29yZCI6IiQyYSQxMCR1d3NvdXl6SlZ5YktkTm0wbUJSa0ZPMWFoUG0yYkZ2WUU1REtFT09lUW82QS4wMzNrR0FhLiIsInJlc291cmNlQ29kZSI6WyJzeXM6YWRtaW46bWVudTp2aWV3Il0sInJvbGVDb2RlIjpbIkFETUlOIl0sImV4cCI6MTY0ODcxODk2MSwidXNlcm5hbWUiOiIxMDQ0NTgyMzYwIn0.szcUDdgkTMjxl4SfPQWJ-fWvn3C2mq3Vx4WHpyOxcSk";
        try {
            DecodedJWT jwt = JWT.decode(toke);
            List<String> resourceCodeList = jwt.getClaim("resourceCode").asList(String.class);
            List<String> roleCodeList = jwt.getClaim("roleCode").asList(String.class);
            String username = jwt.getClaim("username").asString();
            String password = jwt.getClaim("password").asString();
            System.out.println();
        }
        catch (JWTDecodeException e) {
            e.printStackTrace();
        }
    }


}
