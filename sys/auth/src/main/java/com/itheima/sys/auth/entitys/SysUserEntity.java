package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class SysUserEntity extends BaseEntity<SysUserEntity> {

    /**
     * 账号id
     */
    @TableField(value = "account_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 性别 M男 F女
     */
    @TableField(value = "gender")
    private String gender;


}
