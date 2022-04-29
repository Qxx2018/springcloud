package com.itheima.sys.auth.config;



import com.itheima.sys.auth.config.customfilter.JwtWebFilter;
import com.itheima.sys.auth.config.manager.AuthorizationManager;
import com.itheima.sys.auth.config.manager.JwtSecurityContextRepository;
import com.itheima.sys.auth.handler.AccessDeniedHandler;
import com.itheima.sys.auth.handler.LoginFailedHandler;
import com.itheima.sys.auth.handler.LoginSuccessHandler;
import com.itheima.sys.auth.service.SysAccountService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Resource;

/**
 * 使用WebFlux的Security配置类
 * @author 10445
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Resource
    private SysAccountService sysAccountService;
    @Resource
    private AuthorizationManager authorizationManager;
    @Resource
    private JwtWebFilter jwtWebFilter;
    @Resource
    private JwtSecurityContextRepository jwtSecurityContextRepository;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailedHandler loginFailedHandler;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager
                = new UserDetailsRepositoryReactiveAuthenticationManager(sysAccountService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange()
                //无需进行权限过滤的请求路径
                .pathMatchers("/login").permitAll()
                //无需权限过滤的请求方式
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/**").access(authorizationManager)
                .anyExchange().authenticated()
                .and()
                .addFilterAfter(jwtWebFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(jwtSecurityContextRepository)
                .formLogin()
                .loginPage("/login")
                .authenticationSuccessHandler(loginSuccessHandler)
                .authenticationFailureHandler(loginFailedHandler)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and().cors().disable().csrf().disable();
        return http.build();
    }

}
