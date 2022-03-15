package com.itheima.sys.coredata.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色
 * @author 10445
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -6817794327619768768L;

    /**
     * 角色id
     */
    private String id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 角色名字
     */
    private String roleName;


}
