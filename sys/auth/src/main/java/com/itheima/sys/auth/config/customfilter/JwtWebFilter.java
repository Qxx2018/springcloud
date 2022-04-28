package com.itheima.sys.auth.config.customfilter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.corebase.constants.AuthConstants;
import com.itheima.sys.coredata.dto.response.rsp.Rsp;
import com.itheima.sys.coredata.dto.response.rsp.RspCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;



/**
 * 请求来的第一步就要进行JWT的过滤和校验，
 * 如果OK在交给SpringSecurity将JWT的内容解析出来，
 * 所以这个过滤器只是一个教研JWT是否有效的作用，并没有对当前请求授权：
 * @author 10445
 */
@Component
@Slf4j
public class JwtWebFilter implements WebFilter {

    /**
     * 权限服务
     * 这里需要
     * 验证jwt-token合理性
     */
    private SysAuthService sysAuthService;

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link WebFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        String path = request.getPath().value();
        if (path.contains(AuthConstants.LOGIN_URL)) {
            //是登入地址不进行JWT过滤
            return chain.filter(exchange);
        }
        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (Strings.isBlank(token)) {
            //token为空
            DataBuffer buffer = response.bufferFactory().wrap(JSONObject.toJSONString(Rsp.error(RspCode.BUSINESS_LOGIN_DISABLED)).getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        //验证token
        boolean valid = sysAuthService.verifyJwtToken(token);
        if (!valid) {
            DataBuffer buffer = response.bufferFactory().wrap(JSONObject.toJSONString(Rsp.error(RspCode.BUSINESS_LOGIN_DISABLED)).getBytes());
        }
        //验证token里的数据是否存在且在系统中有值

        return chain.filter(exchange);

    }
}
