package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色表
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class SysRoleEntity extends BaseEntity<SysRoleEntity> {

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 角色类型
     */
    @TableField(value = "role_type")
    private String roleType;

    /**
     * 角色名字
     */
    @TableField(value = "role_name")
    private String roleName;


}
