package com.itheima.sys.coredata.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统角色请求
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleReqDto implements Serializable {

    private static final long serialVersionUID = -8097663498912280485L;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不为空")
    private String roleCode;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 角色名字
     */
    @NotBlank(message = "角色名字不为空")
    private String roleName;

}
