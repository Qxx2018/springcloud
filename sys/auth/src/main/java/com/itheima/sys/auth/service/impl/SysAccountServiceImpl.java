package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.mapper.SysAccountMapper;
import com.itheima.sys.auth.service.SysAccountService;
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
    private SysAccountMapper sysAccountMapper;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysResourceService sysResourceService;

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
     * loadUserByUsername在登录的时候会触发该方法
     * 根据用户名定位用户
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<SysAccountEntity> wrapper = new LambdaQueryWrapper<SysAccountEntity>()
                .eq(SysAccountEntity::getUsername,username)
                .eq(SysAccountEntity::getDeleted, Constants.NOT_DELETE);
        SysAccountEntity accountEntity = sysAccountMapper.selectOne(wrapper);
        if (Objects.isNull(accountEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //用户的角色
        //查询该登入账户下的角色下的权限资源
        List<RoleVo> roleVos = sysRoleService.allRoleByAccountId(accountEntity.getId());
        List<Long> ids = roleVos.stream().map(r -> Long.parseLong(r.getId())).collect(Collectors.toList());
        List<ResourceVo> resourceVos = sysResourceService.allResourceByRoleIds(ids);
        List<GrantedAuthority> auths = resourceVos.stream().map(r -> new SimpleGrantedAuthority(r.getResourceCode()))
                .collect(Collectors.toList());
        accountEntity.setRoles(auths);

        return accountEntity;
    }
}
