package com.itheima.sys.auth.config;

import com.itheima.sys.auth.config.customfilter.CustomFilterInvocationSecurityMetadataSource;
import com.itheima.sys.auth.config.customfilter.JwtAuthenticationFilter;
import com.itheima.sys.auth.config.customfilter.LoginAuthenticationFilter;
import com.itheima.sys.auth.config.manager.CustomAccessDecisionManager;
import com.itheima.sys.auth.handler.AuthenticationAccessDeniedHandler;
import com.itheima.sys.auth.service.SysAccountService;
import com.itheima.sys.auth.service.SysAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * web权限认证配置
 * 继承spring security WebSecurityConfigurerAdapter
 * @author 10445
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SysAccountService sysAccountService;
    @Resource
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Resource
    private CustomAccessDecisionManager customAccessDecisionManager;
    @Resource
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    @Resource
    private SysAuthService authService;
    /**
     * 可以细粒度的配制验证模式和哪些地址对应哪些角色或权限
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //通过FilterInvocationSecurityMetadataSource 动态角色
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>() {

                            /**
                             * Initialize the object possibly returning a modified instance that should be used
                             * instead.
                             *
                             * @param object the object to initialize
                             * @return the initialized version of the object
                             */
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                                //自定义 自定义权限拦截  加载访问时所需要的具体资源权限（请求地址获取资源权限）
                                object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                                //自定义访问决策管理器（登入用户的资源权限与请求地址获取资源权限比较，是否通过）
                                object.setAccessDecisionManager(customAccessDecisionManager);
                                return object;
                            }
                        }
                )
                //所有接口都需要进行认证才可以访问
                .antMatchers("/**")
                //指定经过身份验证且不被“记住”的用户允许url
                    .fullyAuthenticated()
                .and()
                //认证方式表单形式
                .formLogin()
                .loginProcessingUrl("/login")
                    //任何人都能访问的url
                    .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),authService))
                .addFilter(new LoginAuthenticationFilter(authenticationManager(),authService))
                .csrf()
                    //关闭csrf
                    .disable()
                //无状态session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        //无视创建登入账户的地址
        web.ignoring().antMatchers("/ssa/account/create");
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 用户动态认证
         * loadUserByUsername在登录的时候会触发该方法
         */
        auth.userDetailsService(sysAccountService).passwordEncoder(password());
    }

}
