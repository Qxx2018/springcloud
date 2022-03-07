package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 管理后台菜单表
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class SysMenuEntity extends BaseEntity<SysMenuEntity> {

    /**
     * 前端菜单资源编码
     */
    @TableField(value = "resource_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceId;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单名
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单编码
     */
    @TableField(value = "menu_code")
    private String menuCode;

    /**
     * 菜单等级
     */
    @TableField(value = "menu_level")
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    @TableField(value = "menu_sort")
    private Integer menuSort;

    /**
     * 菜单图标id
     */
    @TableField(value = "menu_icon_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuIconId;

    /**
     * 图标名称
     */
    @TableField(value = "menu_icon_svg_name")
    private String menuIconSvgName;

    /**
     * 菜单图标url
     */
    @TableField(value = "menu_icon_url")
    private String menuIconUrl;

    /**
     * 菜单层级 树
     */
    @TableField(value = "menu_tree_path")
    private String menuTreePath;

    /**
     * 菜单形式
     */
    @TableField(value = "category")
    private String category;

    /**
     * 菜单类型
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * 菜单链接
     */
    @TableField(value = "menu_url")
    private String menuUrl;

    /**
     * 显示或隐藏
     */
    @TableField(value = "hidden")
    private String hidden;


}
