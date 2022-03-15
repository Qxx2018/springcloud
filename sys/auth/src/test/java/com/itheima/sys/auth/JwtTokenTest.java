package com.itheima.sys.auth;

import com.itheima.sys.SysAuthApplication;
import com.itheima.sys.auth.config.jwt.ScJwtConfig;
import com.itheima.sys.auth.utils.AuthToolsFunc;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import com.itheima.sys.coredata.dto.base.Jwt.JwtTokenDo;
import com.itheima.sys.coredata.dto.base.Jwt.PayLoadDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SysAuthApplication.class)
public class JwtTokenTest {

    @Resource
    private ScJwtConfig scJwtConfig;
    @Test
    public void sign() {
        JwtTokenDo jwtTokenDo = new JwtTokenDo();
        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type",scJwtConfig.getHeaderType());
        header.put("alg",scJwtConfig.getHeaderAlg());

        //负载PayLoadDo
        PayLoadDo payLoadDo = new PayLoadDo();
        payLoadDo.setUsername("1044582360");
        payLoadDo.setPassword(AuthToolsFunc.encodePassword("1044582360"));
        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("ADMIN");
        List<String> resourceCodeList = new ArrayList<>();
        resourceCodeList.add("sys:admin:menu:view");
        payLoadDo.setRoleCodeList(roleCodeList);
        payLoadDo.setResourceCodeList(resourceCodeList);
        jwtTokenDo.setHeader(header);
        jwtTokenDo.setTime(scJwtConfig.getDurationDayTime());
        jwtTokenDo.setTokenSecret(scJwtConfig.getTokenSecret());
        jwtTokenDo.setPayLoadDo(payLoadDo);
        String token = JwtTokenUtils.sign(jwtTokenDo);
        System.out.println(token);
    }

}
