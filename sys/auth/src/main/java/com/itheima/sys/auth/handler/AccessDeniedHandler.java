package com.itheima.sys.auth.handler;

import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.coredata.dto.response.rsp.Rsp;
import com.itheima.sys.coredata.dto.response.rsp.RspCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.io.UnsupportedEncodingException;

/**
 * 无权访问时的提示Handler
 * @author 10445
 */
@Component
public class AccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Rsp<String> rsp = Rsp.error(RspCode.BUSINESS_INSUFFICIENT_PRIVILEGES);

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
