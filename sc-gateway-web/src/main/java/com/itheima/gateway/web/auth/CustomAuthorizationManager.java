package com.itheima.gateway.web.auth;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义授权管理器
 * @author 10445
 */
@Component
@AllArgsConstructor
@Slf4j
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerWebExchange webExchange = authorizationContext.getExchange();
        ServerHttpRequest httpRequest = webExchange.getRequest();
        String path = httpRequest.getURI().getPath();

        //带通配符的可以使用这个进行匹配
        PathMatcher pathMatcher = new AntPathMatcher();

        //对于跨域的预检请求直接放行
        if (HttpMethod.OPTIONS.equals(httpRequest.getMethod())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //token为空的拒绝访问
        String token = httpRequest.getHeaders().getFirst("Authorization");
        if (StrUtil.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return null;

    }

}
