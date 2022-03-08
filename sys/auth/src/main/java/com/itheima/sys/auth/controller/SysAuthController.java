package com.itheima.sys.auth.controller;

import com.itheima.sys.auth.service.SysAccountService;
import com.itheima.sys.auth.service.SysMenuService;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.coredata.dto.request.SysAccountReqDto;
import com.itheima.sys.coredata.dto.request.SysMenuReqDto;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;
import com.itheima.sys.coredata.dto.request.SysRoleReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/ssa")
public class SysAuthController {

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


}
