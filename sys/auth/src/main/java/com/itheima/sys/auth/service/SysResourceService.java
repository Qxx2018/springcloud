package com.itheima.sys.auth.service;

import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.corebase.service.BaseService;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;
import com.itheima.sys.coredata.dto.response.ResourceVo;

import java.util.List;

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

    /**
     * 根据请求url查询该url需要的权限资源
     * @param url
     * @return
     */
    List<ResourceVo> allResourceByUrl(String url);

    /**
     * 根据角色ids获取角色下所有的资源权限
     * @param ids 角色ids
     * @return
     */
    List<ResourceVo> allResourceByRoleIds(List<Long> ids);
}
