package com.itheima.sys.auth.service;

import com.itheima.sys.auth.entitys.SysMenuEntity;
import com.itheima.sys.corebase.service.BaseService;
import com.itheima.sys.coredata.dto.request.SysMenuReqDto;

/**
 * 系统菜单服务
 *
 * @author 10445
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {
    /**
     * 创建菜单
     * @param dto 创建菜单dto
     * @return
     */
    Boolean createMenu(SysMenuReqDto dto);
}
