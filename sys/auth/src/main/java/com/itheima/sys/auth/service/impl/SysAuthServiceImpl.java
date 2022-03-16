package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.sys.auth.config.jwt.ScJwtConfig;
import com.itheima.sys.auth.dto.AuthPermissionDo;
import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.mapper.SysAccountMapper;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.auth.utils.AuthToolsFunc;
import com.itheima.sys.corebase.constants.AuthConstants;
import com.itheima.sys.corebase.constants.Constants;
import com.itheima.sys.corebase.utils.JwtTokenUtils;
import com.itheima.sys.corebase.utils.RedisUtil;
import com.itheima.sys.coredata.dto.base.jwt.JwtTokenDo;
import com.itheima.sys.coredata.dto.base.jwt.PayLoadDo;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import com.itheima.sys.coredata.dto.response.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统权限服务实现
 * @author 10445
 */
@Slf4j
@Service
public class SysAuthServiceImpl implements SysAuthService {

    @Resource
    private SysAccountMapper sysAccountMapper;
    @Resource
    private SysResourceService sysResourceService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private RedisUtil<Map<String,String>> redisUtil;
    @Resource
    private ScJwtConfig scJwtConfig;
    /**
     * 往redis存储权限
     *
     * @return
     */
    @Override
    public boolean storeAuth() {
        //查询权限资源
        Wrapper<SysResourceEntity> query = Wrappers.<SysResourceEntity>lambdaQuery()
                .select(SysResourceEntity::getResourceUrl,SysResourceEntity::getResourceCode)
                .eq(SysResourceEntity::getDeleted, Constants.NOT_DELETE);
        List<SysResourceEntity> sysResourceEntityList = sysResourceService.list(query);
        //list转map
        Map<String, String> maps = sysResourceEntityList.stream().collect(
                Collectors.toMap(
                        SysResourceEntity::getResourceUrl, SysResourceEntity::getResourceCode, (key1, key2) -> key2
                )
        );
        redisUtil.ptSet(AuthConstants.SYSAUTHREDISKEY, maps);
        return Boolean.TRUE;
    }

    /**
     * 从redis读取权限
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, String> readAuth(String key) {
        return redisUtil.ptGet(key);
    }

    /**
     * 用户登入后根据用户账户账号获取用户账号信息，角色，资源权限
     *
     * @param username 登入账号
     * @return
     */
    @Override
    public AuthPermissionDo authPermissionByUserName(String username) {

        AuthPermissionDo authPermissionDo = new AuthPermissionDo();

        LambdaQueryWrapper<SysAccountEntity> wrapper = new LambdaQueryWrapper<SysAccountEntity>()
                .eq(SysAccountEntity::getUsername,username)
                .eq(SysAccountEntity::getDeleted, Constants.NOT_DELETE);
        SysAccountEntity accountEntity = sysAccountMapper.selectOne(wrapper);
        if (Objects.isNull(accountEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //用户的角色
        //查询该登入账户下的角色下的权限资源
        List<RoleVo> roleVos = sysRoleService.allRoleByAccountId(accountEntity.getId());
        List<Long> ids = roleVos.stream().map(r -> Long.parseLong(r.getId())).collect(Collectors.toList());
        List<ResourceVo> resourceVos = sysResourceService.allResourceByRoleIds(ids);

        authPermissionDo.setSysAccountEntity(accountEntity);
        authPermissionDo.setResourceVos(resourceVos);
        authPermissionDo.setRoleVos(roleVos);

        return authPermissionDo;

    }

    /**
     * 用户登入后根据用户账户信息生成jwt-token
     *
     * @param username 登入账号
     * @return
     */
    @Override
    public String createJwtTokenAfterLogin(String username) {

        AuthPermissionDo authPermissionDo = this.authPermissionByUserName(username);
        JwtTokenDo jwtTokenDo = new JwtTokenDo();
        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type",scJwtConfig.getHeaderType());
        header.put("alg",scJwtConfig.getHeaderAlg());

        //负载PayLoadDo
        PayLoadDo payLoadDo = new PayLoadDo();
        payLoadDo.setUsername(authPermissionDo.getSysAccountEntity().getUsername());
        payLoadDo.setPassword(authPermissionDo.getSysAccountEntity().getPassword());

        payLoadDo.setRoleCodeList(
                authPermissionDo.getRoleVos().stream().map(RoleVo::getRoleCode).collect(Collectors.toList())
        );
        payLoadDo.setResourceCodeList(
                authPermissionDo.getResourceVos().stream().map(ResourceVo::getResourceCode).collect(Collectors.toList())
        );

        jwtTokenDo.setHeader(header);
        jwtTokenDo.setTime(scJwtConfig.getDurationDayTime());
        jwtTokenDo.setTokenSecret(scJwtConfig.getTokenSecret());
        jwtTokenDo.setPayLoadDo(payLoadDo);

        String token = JwtTokenUtils.sign(jwtTokenDo);
        log.info("jwt-token:"+token);

        return token;

    }

    /**
     * 验证jwt-token
     *
     * @param token       token
     * @return
     */
    @Override
    public Boolean verifyJwtToken(String token) {

        return JwtTokenUtils.verify(token,scJwtConfig.getTokenSecret());

    }

    /**
     * 从token中获取数据 [账户账号密码 角色 资源权限]
     *
     * @param token
     * @return
     */
    @Override
    public AuthPermissionDo analyJwtToken(String token) {
        PayLoadDo payLoadDo = JwtTokenUtils.analy(token);
        AuthPermissionDo authPermissionDo = this.authPermissionByUserName(payLoadDo.getUsername());
        return authPermissionDo;
    }
}
