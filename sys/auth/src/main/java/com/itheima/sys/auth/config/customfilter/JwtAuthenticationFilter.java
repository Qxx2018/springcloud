package com.itheima.sys.auth.config.customfilter;

import com.itheima.sys.auth.dto.AuthPermissionDo;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p color="red">自定义Token过滤器</p>
 *
 * 登录成功后，我们使用Jwt生成了一串Token并返还给用户，以后的所有请求都需要携带该Token
 *
 * Security 默认的是从Session中获取用户信息，
 * 显然也不符合我们的要求，所以下面我们要重写自己的Token过滤器。
 * 需要实现BasicAuthenticationFilter接口
 * @author 10445
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    /**
     * 权限服务
     * 这里需要
     * 用户登入后根据用户账户账号生成jwt-token
     */
    private SysAuthService sysAuthService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SysAuthService sysAuthService) {
        super(authenticationManager);
        this.sysAuthService = sysAuthService;

    }
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        response.setContentType("text/json;charset=utf-8");
        //从请求中获取token
        String token = request.getHeader("token");
        //token不存在
        if (StringUtils.isEmpty(token)) {
            response.getWriter().write("登入失败");
            return;
        }
        //验证token
        boolean isold = sysAuthService.verifyJwtToken(token);
        if (!isold) {
            //验证失败
            response.getWriter().write("登入失败");
            return;
        }
        //通过token获取账户信息 角色 权限信息
        AuthPermissionDo authPermissionDo = sysAuthService.analyJwtToken(token);
        List<GrantedAuthority> authorityList = authPermissionDo.getResourceVos().stream().map(
                r-> new SimpleGrantedAuthority(r.getResourceCode())
        ).collect(Collectors.toList());
        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authPermissionDo.getSysAccountEntity(),null,authorityList);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("AuthPermissionDo",authPermissionDo);
            //全部ok => 携带登入用户的权限对象放行该过滤器
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("登入失败");
        }
    }
}
