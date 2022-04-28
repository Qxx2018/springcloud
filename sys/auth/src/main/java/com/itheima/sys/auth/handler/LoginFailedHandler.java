package com.itheima.sys.auth.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.coredata.dto.response.rsp.Rsp;
import com.itheima.sys.coredata.dto.response.rsp.RspCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.io.UnsupportedEncodingException;

/**
 * 登录失败的Handler
 * @author 10445
 */
@Component
public class LoginFailedHandler implements ServerAuthenticationFailureHandler {

    /**
     * Invoked when authentication attempt fails
     *
     * @param webFilterExchange the exchange
     * @param exception         the reason authentication failed
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Rsp<String> rsp = Rsp.error(RspCode.BUSINESS_LOGIN_ERROR);

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
