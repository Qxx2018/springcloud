package com.itheima.sys.auth.service;

import com.itheima.sys.auth.entitys.SysRoleEntity;
import com.itheima.sys.corebase.service.BaseService;
import com.itheima.sys.coredata.dto.request.SysRoleReqDto;
import com.itheima.sys.coredata.dto.response.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色
 * @author 10445
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {
    /**
     * 创建系统角色
     * @param dto 角色信息
     * @return
     */
    Boolean createRole(SysRoleReqDto dto);

    /**
     * 通过账户id查询该账户的角色
     * @param accountId 账户id
     * @return
     */
    List<RoleVo> allRoleByAccountId(Long accountId);

}
