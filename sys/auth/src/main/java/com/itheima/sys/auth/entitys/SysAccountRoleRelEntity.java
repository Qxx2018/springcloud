package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 账户角色关联表
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_account_role_rel")
public class SysAccountRoleRelEntity extends BaseEntity<SysAccountRoleRelEntity> {
    /**
     * 账号id
     */
    @TableField(value = "account_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}
