package com.itheima.sys.auth.service;

import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.corebase.service.BaseService;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;

/**
 * 系统资源服务【权限资源】
 * @author 10445
 */
public interface SysResourceService extends BaseService<SysResourceEntity> {
    /**
     * 创建权限资源
     * @param dto 权限资源请求
     * @return
     */
    Boolean createResource(SysResourceReqDto dto);
}
