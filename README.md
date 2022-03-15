# springcloud
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