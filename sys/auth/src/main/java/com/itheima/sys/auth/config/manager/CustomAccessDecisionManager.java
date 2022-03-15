package com.itheima.sys.auth.config.manager;

import com.itheima.sys.corebase.constants.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 权限决策 AccessDecisionManager
 * 有了资源权限(FilterInvocationSecurityMetadataSource) 知道了访问的url需要的具体权限 ，接下来
 * 就是决策当前的访问是否通过权限验证了
 *
 * 这需要通过自定义AccessDecisionManager来实现
 *
 * 自定义访问决策管理器
 * @author 10445
 */
@Component
@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {


    /**
     * 为传递的参数解析访问控制决策
     * Resolves an access control decision for the passed parameters.
     *
     * @param authentication   the caller invoking the method (not null)
     * @param object           the secured object being called
     * @param configAttributes the configuration attributes associated with the secured
     *                         object being invoked
     * @throws AccessDeniedException               if access is denied as the authentication does not
     *                                             hold a required authority or ACL privilege
     * @throws InsufficientAuthenticationException if access is denied as the
     *                                             authentication does not provide a sufficient level of trust
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //对于为登入的匿名用户 authentication.getAuthorities() 返回的是 “ROLE_ANONYMOUS”
        if (Objects.isNull(authentication) || CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException("当前访问没有权限");
        }
        //获取登入用户具有的角色所属的权限资源
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //循环当前url需要的权限资源
        for (ConfigAttribute configAttribute : configAttributes) {
            //遍历用户具有的角色所属的权限资源
            for (GrantedAuthority grantedAuthority : authorities) {
                if (AuthConstants.ROLE_ANONYMOUS.equals(grantedAuthority.getAuthority())) {
                    throw new AccessDeniedException("匿名用户访问");
                }
                //如果请求Url需要的角色资源权限是LOGIN，说明当前的Url用户登录后即可访问
                if (AuthConstants.ROLE_LOGIN.equals(grantedAuthority.getAuthority())) {
                    return;
                }
                if (configAttribute.getAttribute().equals(grantedAuthority.getAuthority())) {
                    //相同 => 当前的访问通过权限验证了
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");

    }

    /**
     * Indicates whether this <code>AccessDecisionManager</code> is able to process
     * authorization requests presented with the passed <code>ConfigAttribute</code>.
     * <p>
     * This allows the <code>AbstractSecurityInterceptor</code> to check every
     * configuration attribute can be consumed by the configured
     * <code>AccessDecisionManager</code> and/or <code>RunAsManager</code> and/or
     * <code>AfterInvocationManager</code>.
     * </p>
     *
     * @param attribute a configuration attribute that has been configured against the
     *                  <code>AbstractSecurityInterceptor</code>
     * @return true if this <code>AccessDecisionManager</code> can support the passed
     * configuration attribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * Indicates whether the <code>AccessDecisionManager</code> implementation is able to
     * provide access control decisions for the indicated secured object type.
     *
     * @param clazz the class that is being queried
     * @return <code>true</code> if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
