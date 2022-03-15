package com.itheima.sys.auth;

import com.itheima.sys.SysAuthApplication;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.coredata.dto.response.RoleVo;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SysAuthApplication.class)
public class RoleTest {
    @Autowired
    private SysRoleService sysRoleService;
    @Test
    public void allRoleByAccountId() {
        List<RoleVo> roleVos = sysRoleService.allRoleByAccountId(1501017939711655937L);
        System.out.println();
    }
}
