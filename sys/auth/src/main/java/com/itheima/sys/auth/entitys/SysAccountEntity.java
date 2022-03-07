package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 账户表
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_account")
public class SysAccountEntity extends BaseEntity<SysAccountEntity> {
    /**
     * 登入账号
     */
    @TableField(value = "account_number")
    private String accountNumber;

    /**
     * 登录账号密码
     */
    @TableField(value = "account_password")
    private String accountPassword;

    /**
     * 登录手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

}
