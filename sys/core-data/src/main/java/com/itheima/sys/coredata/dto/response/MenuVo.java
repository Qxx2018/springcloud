package com.itheima.sys.coredata.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单
 * @author 10445
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -7121898176780660151L;

    /**
     * 前端菜单资源编码id
     */
    private String resourceId;


    /**
     * 菜单名
     */
    private String menuName;


}
