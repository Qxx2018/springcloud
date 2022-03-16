package com.itheima.sys.auth.dto;

import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import com.itheima.sys.coredata.dto.response.RoleVo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 账户登入权限对象
 * @author 10445
 */
@Data
public class AuthPermissionDo implements Serializable {

    private static final long serialVersionUID = 4644181931355304760L;
    /**
     * 账户基本信息
     */
    private SysAccountEntity sysAccountEntity;

    /**
     * 账户角色
     */
    private List<RoleVo> roleVos;

    /**
     * 账户资源权限
     */
    private List<ResourceVo> resourceVos;

}
