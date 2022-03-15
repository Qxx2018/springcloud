package com.itheima.sys.coredata.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限资源
 * @author 10445
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVo implements Serializable {

    private static final long serialVersionUID = -6496436923631724657L;

    /**
     * id
     */
    private String id;

    /**
     * 资源名（业务操作名）
     */
    private String resourceName;

    /**
     * 资源url
     */
    private String resourceUrl;

    /**
     * 资源编码
     */
    private String resourceCode;
}

