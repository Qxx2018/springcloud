package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.dto.AuthPermissionDo;
import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.mapper.SysAccountMapper;
import com.itheima.sys.auth.service.SysAccountService;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.auth.utils.AuthToolsFunc;
import com.itheima.sys.corebase.constants.Constants;
import com.itheima.sys.coredata.dto.request.SysAccountReqDto;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import com.itheima.sys.coredata.dto.response.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 登入账户服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements SysAccountService {
    @Resource
    private SysAuthService sysAuthService;

    /**
     * 创建账户
     *
     * @param dto 登入账号信息
     * @return
     */
    @Override
    public Boolean createAccount(SysAccountReqDto dto) {
        dto.setPassword(AuthToolsFunc.encodePassword(dto.getPassword()));
        SysAccountEntity sysAccountEntity = new SysAccountEntity();
        BeanUtils.copyProperties(dto, sysAccountEntity, SysAccountEntity.class);
        this.save(sysAccountEntity);
        return Boolean.TRUE;
    }

    /**
     * findByUsername在登录的时候会触发该方法
     * Find the {@link UserDetails} by username.
     * 根据用户名定位用户
     * @param username the username to look up
     * @return the {@link UserDetails}. Cannot be null
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return Mono.fromCallable(
                () -> {
                    AuthPermissionDo authPermissionDo = sysAuthService.authPermissionByUserName(username);

                    SysAccountEntity accountEntity = authPermissionDo.getSysAccountEntity();

                    List<ResourceVo> resourceVos = authPermissionDo.getResourceVos();

                    List<GrantedAuthority> auths = resourceVos.stream().map(r -> new SimpleGrantedAuthority(r.getResourceCode()))
                            .collect(Collectors.toList());

                    accountEntity.setRoles(auths);

                    return accountEntity;
                }
        );
    }

}
