package com.itheima.sys.corebase.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itheima.sys.coredata.dto.base.jwt.JwtTokenDo;
import com.itheima.sys.coredata.dto.base.jwt.PayLoadDo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * jwt token 工具
 * @author 10445
 */
@Slf4j
public class JwtTokenUtils {

    /**
     * 生成虚拟的JWT token
     * @return
     */
    public static String sign(JwtTokenDo tokenDo) {
        //加密密钥
        String tokenSecret = tokenDo.getTokenSecret();
        //多少天过期
        Integer time = tokenDo.getTime();
        //负载PayLoadDo
        PayLoadDo payLoadDo = tokenDo.getPayLoadDo();
        //账户号
        String username = payLoadDo.getUsername();
        //登入密码
        String password = payLoadDo.getPassword();
        //角色编码
        List<String> roleCodeList = payLoadDo.getRoleCodeList();
        //资源权限
        List<String> resourceCodeList = payLoadDo.getResourceCodeList();

        //私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);


        //设置过期时间
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        //过期按天算
        cd.add(Calendar.DATE, time);
        Date date = cd.getTime();


        String token =
                JWT.create()
                        .withHeader(tokenDo.getHeader())
                        .withClaim("username",username)
                        .withClaim("password", password)
                        .withArrayClaim("roleCode", roleCodeList.toArray(new String[roleCodeList.size()]))
                        .withArrayClaim("resourceCode",resourceCodeList.toArray(new String[resourceCodeList.size()]))
                        .withExpiresAt(date)
                        .sign(algorithm);
        return token;
    }

    /**
     * 验证token
     * @param token 待验证的token
     * @param tokenSecret 加密密钥
     */
    public static Boolean verify(String token, String tokenSecret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //未验证通过会抛出异常
            verifier.verify(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("验证token未通过:"+e.toString());
        }
        return Boolean.FALSE;
    }

    /**
     * 解析token
     */
    public static PayLoadDo analy(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> resourceCodeList = jwt.getClaim("resourceCode").asList(String.class);
            List<String> roleCodeList = jwt.getClaim("roleCode").asList(String.class);
            String username = jwt.getClaim("username").asString();
            String password = jwt.getClaim("password").asString();

            return PayLoadDo.builder()
                    .password(password)
                    .username(username)
                    .resourceCodeList(resourceCodeList)
                    .roleCodeList(roleCodeList).build();
        }
        catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
