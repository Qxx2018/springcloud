package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色菜单关联
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_menu_rel")
public class SysRoleMenuRelEntity extends BaseEntity<SysRoleMenuRelEntity> {

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;



}
