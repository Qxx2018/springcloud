package com.itheima.sys.auth;

import com.itheima.sys.SysAuthApplication;
import com.itheima.sys.auth.config.jwt.ScJwtConfig;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.auth.utils.AuthToolsFunc;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import com.itheima.sys.coredata.dto.base.jwt.JwtTokenDo;
import com.itheima.sys.coredata.dto.base.jwt.PayLoadDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
    private SysAuthService sysAuthService;

    @Test
    public void sign() {
        System.out.println(sysAuthService.createJwtTokenAfterLogin("1044582360"));
    }

}
