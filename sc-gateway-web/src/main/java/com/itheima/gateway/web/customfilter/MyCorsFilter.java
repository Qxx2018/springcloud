//package com.itheima.gateway.web.filter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.util.pattern.PathPatternParser;
//
///**
// * 自定义
// * 跨域过滤器
// * @author 10445
// */
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class MyCorsFilter {
//
//    @Bean
//    public CorsWebFilter corsFilter() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        //允许访问的请求头
//        configuration.addAllowedHeader("*");
//        //允许请求方法类型
//        configuration.addAllowedMethod("GET");
//        configuration.addAllowedMethod("POST");
//        configuration.addAllowedMethod("OPTIONS");
//        configuration.addAllowedMethod("HEAD");
//        configuration.addAllowedMethod("PUT");
//        configuration.addAllowedMethod("DELETE");
//        configuration.addAllowedMethod("PATCH");
//        //允许向服务器提交请求的URI
//        configuration.addAllowedOrigin("*");
//        //是否允许cookies跨域
//        configuration.setAllowCredentials(true);
//        //预检请求的缓存时间,即在这个时间段里，对于相同的跨域请求不会再预检了
//        configuration.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//        source.registerCorsConfiguration("/**",configuration);
//        return new CorsWebFilter(source);
//    }
//
//}
