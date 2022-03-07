package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 权限资源
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource")
public class SysResourceEntity extends BaseEntity<SysResourceEntity> {

    /**
     * 所属菜单id
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    /**
     * 资源分类id
     */
    @TableField(value = "resource_category_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceCategoryId;

    /**
     * 资源名（业务操作名称）
     */
    @TableField(value = "resource_name")
    private String resourceName;

    /**
     * 资源url （请求访问api地址）
     * 例如：/system/user/delete
     */
    @TableField(value = "resource_url")
    private String resourceUrl;

    /**
     * 资源编码
     */
    @TableField(value = "resource_code")
    private String resourceCode;

    /**
     * 资源级别
     */
    @TableField(value = "resource_level")
    private Integer resourceLevel;

    /**
     * 排序
     * 排序1~9（同级别下的排序）
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 资源图标
     */
    @TableField(value = "resource_icon")
    private String resourceIcon;

    /**
     * 资源类型
     * 例如：按钮button、菜单menu
     */
    @TableField(value = "resource_type")
    private String resourceType;

    /**
     * 描述
     */
    @TableField(value = "resource_description")
    private String resourceDescription;


}
