package com.itheima.sys.auth.config.customfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.DoubleStream;

/**
 * <p color="red">自定义登入过滤器</p>
 *
 * 登录过滤器，需要实现UsernamePasswordAuthenticationFilter接口，
 * 其中attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 表示获取用户用户名密码的入口，
 * 我们可以在这里自定义接受用户名密码，比如以json形式接受，然后再传递给Security，
 * 然后Security下面就会去调用UserDetailsService做用户名和密码的正确性验证，
 * 如果用户名密码正确那就是登录成功，就会触发该实现下的successfulAuthentication方法，
 * 否则就是unsuccessfulAuthentication方法，我们可以在相应的方法中编写相应的提示返回给客户端：
 *
 * 登录成功后，我们使用Jwt生成了一串Token并返还给用户，以后的所有请求都需要携带该Token
 *
 * @author 10445
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 权限服务
     * 这里需要
     * 用户登入后根据用户账户账号生成jwt-token
     */
    private SysAuthService sysAuthService;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager, SysAuthService sysAuthService) {
        this.sysAuthService = sysAuthService;
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * 获取用户名密码入口
     * 自定义接受用户名密码，比如以json形式接受
     * 然后再传递给Security , Security会调用UserDetailsService做用户密码校验
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //以json接收
        response.setContentType("text/json;charset=utf-8");
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ( !Objects.isNull(line = br.readLine()) ) {
                    sb.append(line);
                }
                JSONObject json = JSONObject.parseObject(sb.toString());
                log.info(json.toString());
                String username = json.getString("username");
                String password = json.getString("password");
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            catch (IOException e) {
                e.printStackTrace();
                //返回给浏览器
                response.getWriter().write("参数错误");
            }
        }
        else {
            return super.attemptAuthentication(request, response);
        }
        response.getWriter().write("参数错误");
        return null;

    }

    /**
     * 登入的用户名密码验证正确后触发
     * 根据登入的账户生成 jwt token
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        SysAccountEntity user = (SysAccountEntity) authResult.getPrincipal();
        response.getWriter().write(sysAuthService.createJwtTokenAfterLogin(user.getUsername()));

    }

    /**
     * 登入的用户名密码验证不正确后触发
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        if (failed instanceof UsernameNotFoundException || failed instanceof BadCredentialsException) {
            response.getWriter().write("用户名或密码错误！");
        } else if (failed instanceof DisabledException) {
            response.getWriter().write("账户被禁用，请联系管理员！");
        } else {
            response.getWriter().write("用户名或密码错误！");
        }

    }
}
