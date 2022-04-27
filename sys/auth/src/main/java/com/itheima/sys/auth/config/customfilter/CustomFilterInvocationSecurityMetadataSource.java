//package com.itheima.sys.auth.config.customfilter;
//
//import com.itheima.sys.auth.service.SysResourceService;
//import com.itheima.sys.corebase.constants.AuthConstants;
//import com.itheima.sys.coredata.dto.response.ResourceVo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * <p color="red">自定义权限拦截</p>
// *
// * 自定义：SecurityMetadataSource用于表示对受权限保护的"安全对象"的权限设置信息
// * 通过SecurityMetadataSource来加载访问时所需要的具体资源权限
// *
// * 通过FilterInvocationSecurityMetadataSource
// *
// * 用户动态授权 及 动态角色权限
// * [
// * 实现FilterInvocationSecurityMetadataSource接口，
// * 在这里面根据当前访问的url返回该url所具有的全部权限资源。
// * 但每次访问一次接口都取获取全部的角色肯定性能有所损失。
// * ]
// * @author 10445
// */
//@Component
//@Slf4j
//public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Resource
//    private SysResourceService sysResourceService;
//    /**
//     * 用来实现ant风格的url匹配
//     */
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//    /**
//     * 返回本次访问需要的权限
//     *
//     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
//     *
//     * @param object the object being secured
//     * @return the attributes that apply to the passed in secured object. Should return an
//     * empty collection if there are no applicable attributes.
//     * @throws IllegalArgumentException if the passed object is not of a type supported by
//     *                                  the <code>SecurityMetadataSource</code> implementation
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        //获取当前请求的url
//        //查询当前请求需要的权限
//        String requestUrl = ( (FilterInvocation) object ).getRequestUrl();
//        List<ResourceVo> resourceVos = sysResourceService.allResourceByUrl(requestUrl);
//        if (!CollectionUtils.isEmpty(resourceVos)) {
//            List<ConfigAttribute> configAttributes = resourceVos.stream().map(r -> new SecurityConfig(r.getResourceCode())).collect(Collectors.toList());
//            return configAttributes;
//        }
//        // 不在权限资源表中的url需要用户登入 返回"LOGIN"的权限
//        return  SecurityConfig.createList(AuthConstants.ROLE_LOGIN);
//
//    }
//
//    /**
//     * 返回所有定义的权限资源 , spring security 在启动时校验 ConfigAttribute 是否配置正确
//     * 不需要校验直接返回null
//     *
//     * If available, returns all of the {@code ConfigAttribute}s defined by the
//     * implementing class.
//     * <p>
//     * This is used by the {@link AbstractSecurityInterceptor} to perform startup time
//     * validation of each {@code ConfigAttribute} configured against it.
//     *
//     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
//     */
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    /**
//     * 返回对象是否支持校验 web项目一般使用FilterInvocation来判断，或者直接返回true
//     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
//     * provide {@code ConfigAttribute}s for the indicated secure object type.
//     *
//     * @param clazz the class that is being queried
//     * @return true if the implementation can process the indicated class
//     */
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
