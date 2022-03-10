package com.itheima.sys.auth.controller;

import com.itheima.sys.auth.service.*;
import com.itheima.sys.coredata.dto.request.SysAccountReqDto;
import com.itheima.sys.coredata.dto.request.SysMenuReqDto;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;
import com.itheima.sys.coredata.dto.request.SysRoleReqDto;
import com.itheima.sys.coredata.dto.response.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ssa")
public class SysAuthController {
    @Resource
    private SysAuthService sysAuthService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysAccountService sysAccountService;
    @Resource
    private SysResourceService sysResourceService;
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 创建菜单
     */
    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    public Boolean createMenu(@RequestBody SysMenuReqDto dto) {
        sysMenuService.createMenu(dto);
        return Boolean.TRUE;
    }

    /**
     * 菜单管理
     */
    @RequestMapping(value = "/menu/display", method = RequestMethod.GET)
    public List<MenuVo> menuVoList() {
        return sysMenuService.menuList();
    }

    /**
     * 创建登入账户
     */
    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public Boolean createAccount(@RequestBody SysAccountReqDto dto) {
        sysAccountService.createAccount(dto);
        return Boolean.TRUE;
    }

    /**
     * 创建资源权限
     */
    @RequestMapping(value = "/resource/create", method = RequestMethod.POST)
    public Boolean createResource(@RequestBody SysResourceReqDto dto) {
        sysResourceService.createResource(dto);
        return Boolean.TRUE;
    }

    /**
     * 创建系统角色
     */
    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public Boolean createRole(@RequestBody SysRoleReqDto dto) {
        sysRoleService.createRole(dto);
        return Boolean.TRUE;
    }

    /**
     * redis存储系统资源权限
     */
    @RequestMapping(value = "/authResource/store", method = RequestMethod.POST)
    public Boolean storeAuthResource() {
        sysAuthService.storeAuth();
        return Boolean.TRUE;
    }
    /**
     * 从redis读取权限资源
     */
    @RequestMapping(value = "/authResource/read", method = RequestMethod.GET)
    public Map<String, String> readAuthResource(@RequestParam(value = "key", required = true) String key) {
        Map<String, String> maps =  sysAuthService.readAuth(key);
        return maps;
    }

}
