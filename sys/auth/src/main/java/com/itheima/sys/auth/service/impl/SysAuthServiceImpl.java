package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.service.SysAuthService;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.corebase.constants.AuthConstants;
import com.itheima.sys.corebase.constants.Constants;
import com.itheima.sys.corebase.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统权限服务实现
 * @author 10445
 */
@Slf4j
@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Resource
    private SysResourceService sysResourceService;
    @Resource
    private RedisUtil<Map<String,String>> redisUtil;
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
}
