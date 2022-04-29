package com.itheima.sys.auth.config.manager;

import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Auth鉴权管理器
 * 在WebFlux中，我们需要实现ReactiveAuthorizationManager<AuthorizationContext>类，
 * 并在check方法中进行验权限，check方法可以获取用户登录时的权限和当前请求路径，
 * 如果返回可以访问的标志Mono.just(new AuthorizationDecision(true)) 则可以访问当前地址，否则则无限访问
 * @author 10445
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * Determines if access should be granted for a specific authentication and object
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an empty Mono if authorization is granted or a Mono error if access is
     * denied
     */
    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {

        ServerHttpRequest request = object.getExchange().getRequest();
        //跨域的预检请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS)) {
            return  Mono.just(new AuthorizationDecision(true));
        }

        //获取请求url
        String requestUrl = request.getPath().pathWithinApplication().value();
        //获取请求地址需要的资源权限
        List<ResourceVo> resourceVos = sysResourceService.allResourceByUrl(requestUrl);
        List<String> resourceCodeVos = resourceVos.stream().map(v->v.getResourceCode()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resourceVos)) {
            return  Mono.just(new AuthorizationDecision(false));
        }

        return authentication
                .filter(a->a.isAuthenticated())
                .flatMapIterable(a->a.getAuthorities())
                .map(g->g.getAuthority())
                .any(c->{
                    if (resourceCodeVos.contains(String.valueOf(c))) {
                        return true;
                    }
                    return false;

                })
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
