package com.itheima.sys.auth.controller;

import com.itheima.sys.auth.service.SysMenuService;
import com.itheima.sys.coredata.dto.request.SysMenuReqDto;
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

    /**
     * 创建菜单
     */
    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    public Boolean createMenu(@RequestBody(required = true) SysMenuReqDto dto) {
        sysMenuService.createMenu(dto);
        return Boolean.TRUE;
    }


}
