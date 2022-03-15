package com.itheima.sys.auth;

import com.itheima.sys.SysAuthApplication;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import com.itheima.sys.coredata.dto.response.RoleVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SysAuthApplication.class)
public class ResourceTest {

    @Resource
    private SysResourceService sysResourceService;
    @Resource
    private SysRoleService sysRoleService;
    @Test
    public void allResourceByUrl() {

        List<ResourceVo> resourceVos = sysResourceService.allResourceByUrl("ssa/menu/display");
        System.out.println();

    }

    @Test
    public void allResourceByRoleIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1501079315201155073L);
        List<ResourceVo> resourceVos = sysResourceService.allResourceByRoleIds(ids);
        System.out.println();
    }
    @Test
    public void auth() {
        List<RoleVo> roleVos = sysRoleService.allRoleByAccountId(1501017939711655937L);
        List<Long> ids = roleVos.stream().map(r -> Long.parseLong(r.getId())).collect(Collectors.toList());
        List<ResourceVo> resourceVos = sysResourceService.allResourceByRoleIds(ids);
        List<GrantedAuthority> auths = resourceVos.stream().map(r -> new SimpleGrantedAuthority(r.getResourceCode()))
                .collect(Collectors.toList());
        System.out.println();
    }
}
