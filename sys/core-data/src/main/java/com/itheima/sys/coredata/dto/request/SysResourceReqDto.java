package com.itheima.sys.coredata.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统权限资源请求
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysResourceReqDto implements Serializable {

    private static final long serialVersionUID = -4550059752298157561L;

    /**
     * 所属菜单id
     */
    @NotNull(message = "所属菜单id不为空")
    private Long menuId;

    /**
     * 权限资源名【业务操作】
     */
    @NotBlank(message = "权限资源名不为空")
    private String resourceName;

    /**
     * 权限资源url【请求地址】
     */
    @NotBlank(message = "权限资源url不为空")
    private String resourceUrl;

    /**
     * 权限资源编码
     */
    @NotBlank(message = "权限资源编码不为空")
    private String resourceCode;

    /**
     * 权限资源等级1~9
     */
    @NotNull(message = "权限资源等级不为空")
    private Integer resourceLevel;

    /**
     * 权限资源排序【同级别下的排序】
     */
    @NotNull(message = "权限资源排序不为空")
    private Integer sort;

    /**
     * 权限资源图标
     */
    private String resourceIcon;

    /**
     * 权限资源类型
     */
    private String resourceType;

    /**
     * 描述
     */
    private String resourceDescription;



}
