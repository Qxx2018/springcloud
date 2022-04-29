package com.itheima.sys.auth.config.manager;

import com.itheima.sys.auth.dto.AuthPermissionDo;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import com.itheima.sys.coredata.dto.base.jwt.PayLoadDo;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 解析JWT中用户信息，并授予角色权限信息
 * @author 10445
 */
@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    @Resource
    private SysAuthService sysAuthService;

    /**
     * Saves the SecurityContext
     *
     * @param exchange the exchange to associate to the SecurityContext
     * @param context  the SecurityContext to save
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    /**
     * Loads the SecurityContext associated with the {@link ServerWebExchange}
     *
     * @param exchange the exchange to look up the {@link SecurityContext}
     * @return the {@link SecurityContext} to lookup or empty if not found. Never null
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        //Jwt过滤器处理了校验这里不需要再做一遍JWT校验
        String token = exchange.getRequest().getHeaders().getFirst("token");
        //根据token获取用户信息
        AuthPermissionDo authPermissionDo = sysAuthService.analyJwtToken(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authPermissionDo.getSysAccountEntity().getUsername(),
                authPermissionDo.getSysAccountEntity().getPassword()
        );
        return new ReactiveAuthenticationManager() {

            /**
             * Attempts to authenticate the provided {@link Authentication}
             *
             * @param authentication the {@link Authentication} to test
             * @return if authentication is successful an {@link Authentication} is returned. If
             * authentication cannot be determined, an empty Mono is returned. If authentication
             * fails, a Mono error is returned.
             */
            @Override
            public Mono<Authentication> authenticate(Authentication authentication) {
                return Mono.fromCallable(()->{
                    AuthPermissionDo authPermissionDo = sysAuthService.analyJwtToken(token);

                    List<GrantedAuthority> authorityList = authPermissionDo.getResourceVos()
                            .stream().map(r-> new SimpleGrantedAuthority(r.getResourceCode()))
                            .collect(Collectors.toList());
                    //账号密码和资源权限
                    return new UsernamePasswordAuthenticationToken(
                            authPermissionDo.getSysAccountEntity().getUsername(),
                            authPermissionDo.getSysAccountEntity().getPassword(),
                            authorityList
                    );
                });
            }
        }.authenticate(authentication).map(SecurityContextImpl::new);

    }
}
