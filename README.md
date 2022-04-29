# springcloud
github 1044582360@qq.com 密码13WACB845qxx

=============================================================================================================
====SpringSecurity web 动态认证
=============================================================================================================
2022-03-11
    用户动态认证：
        1、web权限认证配置，再登入时调用loadUserByUsername方法 判断登入用户账户与密码
        2、账户注册的请求无需认证
2022-03-15
    用户动态认证
        1、实现FilterInvocationSecurityMetadataSource接口，根据当前访问的url返回该url所具有的全部权限资源
        2、权限决策 AccessDecisionManager，决策当前的访问是否通过权限验证了 （操作： 循环当前url需要的权限资源,遍历用户具有的角色所属的权限资源）
        3、form表单的形式的登录 接再来需要变成JSON形式的
    JWT
        1、配置文件ScJwtConfig  位配置中心 Jwt.yaml
2022-03-16
    根据登入的账户生成token
        1、jwt token 数据格式 JwtTokenDo
        2、解析token 获取从token中获取数据 [账户账号密码 角色 资源权限]
        3、接下来 处理 自定义Token过滤器 JwtAuthenticationFilter
2022-03-16
    SpringSecurity web 动态认证和动态权限角色 整合JWT使用 Token 认证授权
    权限认证流程图见【1】
=============================================================================================================
====SpringSecurity - WebFlux环境下实现用户动态认证
=============================================================================================================
2022-03-17 
    网关调整为webflux环境
    auth服务调整为webflux环境
=============================================================================================================
====SpringSecurity - WebFlux环境下动态角色权限
====SpringSecurity - WebFlux环境下整合JWT使用 Token 认证授权
=============================================================================================================
2022-04-29
    权限资源配置WebSecurityConfig
        1、安全过滤器链SecurityWebFilterChain
            a.无需权限过滤的请求路径【一般配置白名单】
            b.无需权限过滤的请求方式
            c.指定请求的路径配置Auth鉴权管理器AuthorizationManager
            d..anyExchange().access() 其他的鉴权管理器
            e.添加JwtWebFilter
            f.JwtSecurityContextRepository,解析JWT中用户信息，并授予角色权限信息
            g.登录成功、失败处理;鉴权失败处理
        2、AuthorizationManager  
            a.获取请求方式->放行特定的比如HttpMethod.OPTIONS
            b.获取请求地址->查询资源权限
            c.用户角色所包含的资源权限中是否存在该请求地址所属的资源权限=>用户是否有访问该地址的权力
        3、JwtWebFilter
            a.是登录地址不进行JWT过滤
            b.获取jwt-token  并验证token合法性
        4、解析JWT中用户信息，并授予角色权限信息JwtSecurityContextRepository
            a.从token中获取用户的账号密码以及权限资源
                return new UsernamePasswordAuthenticationToken(
                           authPermissionDo.getSysAccountEntity().getUsername(),
                           authPermissionDo.getSysAccountEntity().getPassword(),
                           authorityList
                );
        5、LoginSuccessHandler
            返回登入成功信息以及token
=============================================================================================================
====springcloud-frame-base-v1.0【gateway-auth】    
=============================================================================================================       
2022-04-29
    springcloud-frame-base-v1.0【gateway-auth】    
    
