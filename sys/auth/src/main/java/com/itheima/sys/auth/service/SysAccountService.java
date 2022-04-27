package com.itheima.sys.auth.service;

import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.corebase.service.BaseService;
import com.itheima.sys.coredata.dto.request.SysAccountReqDto;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 登入账户服务
 * 在Webflux中就不是UserDetails ，现在要换成ReactiveUserDetailsService接口了
 * @author 10445
 */
public interface SysAccountService extends BaseService<SysAccountEntity>, ReactiveUserDetailsService {
    /**
     * 创建账户
     * @param dto 登入账号信息
     * @return
     */
    Boolean createAccount(SysAccountReqDto dto);
}
