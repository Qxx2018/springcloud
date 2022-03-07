package com.itheima.sys.coredata.dto.request;



import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统菜单请求dto
 * @author 10445
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuReqDto implements Serializable {

    private static final long serialVersionUID = -8205474372909209426L;

    /**
     * 上级菜单id--父级id 0：跟菜单
     */
    @NotNull(message="父级菜单id不为空")
    private Long parentId;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名称不为空")
    @Length(min = 1, max = 20, message = "菜单名不为空")
    private String menuName;

    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码")
    @Length(min = 1, max = 20, message = "菜单编码不为空")
    private String menuCode;

    /**
     * 菜单等级
     */
    @NotNull(message = "菜单等级不为空")
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    @NotNull(message = "菜单排序不为空")
    private Integer menuSort;

    /**
     * 菜单图标id
     */
    private Long menuIconId;

    /**
     * 菜单图标名称
     */
    private String menuIconSvgName;

    /**
     * 菜单图标url
     */
    private String menuIconUrl;

    /**
     * 菜单层级 x/y/z
     * x,y,z值是每级id
     */
    private String menuTreePath;

    /**
     * 菜单形式
     */
    private String category;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单链接
     */
    private String menuUrl;

    /**
     * 菜单显示或隐藏
     */
    private String hidden;




}
