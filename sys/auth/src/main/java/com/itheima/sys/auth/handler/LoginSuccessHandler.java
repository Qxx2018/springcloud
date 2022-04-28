package com.itheima.sys.auth.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.coredata.dto.response.rsp.Rsp;
import com.itheima.sys.coredata.dto.response.rsp.RspCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.io.UnsupportedEncodingException;

/**
 * 登录成功的Handler
 * @author 10445
 */
@Component
public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    /**
     * 权限服务
     * 这里需要
     * 登入成功后生成jwt-token
     */
    private SysAuthService sysAuthService;

    /**
     * Invoked when the application authenticates successfully
     *
     * @param webFilterExchange the exchange
     * @param authentication    the {@link Authentication}
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        String token = sysAuthService.createJwtTokenAfterLogin(username);

        Rsp<String> rsp = Rsp.ok(RspCode.BUSINESS_LOGIN_SUCCESS,token);

        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Mono<Void> ret = null;
        try {
            ret = response.writeAndFlushWith(
                    Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(JSONObject.toJSONString(rsp).getBytes("UTF-8"))))
            );
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
