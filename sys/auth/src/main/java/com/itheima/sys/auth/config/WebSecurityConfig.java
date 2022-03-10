package com.itheima.sys.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * web权限认证配置
 * 继承spring security WebSecurityConfigurerAdapter
 * @author 10445
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 可以细粒度的配制验证模式和哪些地址对应哪些角色或权限
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //所有接口都需要进行认证才可以访问
                .antMatchers("/**")
                    //指定经过身份验证且不被“记住”的用户允许url
                    .fullyAuthenticated()
                .and()
                //认证方式表单形式
                .formLogin()
                    //任何人都能访问的url
                    .permitAll()
                .and()
                .csrf()
                    //关闭csrf
                    .disable();
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加二个用户
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456")).authorities("admin");
        auth.inMemoryAuthentication()
                .withUser("common")
                .password(new BCryptPasswordEncoder().encode("654321")).authorities("common");

    }

}