package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.mapper.SysResourceMapper;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.corebase.constants.Constants;
import com.itheima.sys.corebase.utils.BeanCopyUtils;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统权限资源服务
 * @author 10445
 */
@Service
@Slf4j
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResourceEntity> implements SysResourceService {
    @Resource
    private SysResourceMapper sysResourceMapper;
    /**
     * 创建权限资源
     *
     * @param dto 权限资源请求
     * @return
     */
    @Override
    public Boolean createResource(SysResourceReqDto dto) {
        SysResourceEntity sysResourceEntity = new SysResourceEntity();
        BeanUtils.copyProperties(dto, sysResourceEntity);
        this.save(sysResourceEntity);
        return Boolean.TRUE;
    }

    /**
     * 根据请求url查询该url需要的权限资源
     *
     * @param url
     * @return
     */
    @Override
    public List<ResourceVo> allResourceByUrl(String url) {
        Wrapper<SysResourceEntity> query = Wrappers.<SysResourceEntity>lambdaQuery()
                .select(
                        SysResourceEntity::getId,
                        SysResourceEntity::getResourceUrl,
                        SysResourceEntity::getResourceCode,
                        SysResourceEntity::getResourceName
                        )
                .eq(SysResourceEntity::getResourceUrl, url)
                .eq(SysResourceEntity::getDeleted, Constants.NOT_DELETE);
        List<SysResourceEntity> sysResourceEntities = this.list(query);
        List<ResourceVo> resourceVos = BeanCopyUtils.convertList(sysResourceEntities, ResourceVo.class);

        return resourceVos;
    }

    /**
     * 根据角色ids获取角色下所有的资源权限
     *
     * @param ids 角色ids
     * @return
     */
    @Override
    public List<ResourceVo> allResourceByRoleIds(List<Long> ids) {
        List<SysResourceEntity> resourceEntities = sysResourceMapper.allResourceByRoleIds(ids);
        List<ResourceVo> resourceVos = BeanCopyUtils.convertList(resourceEntities, ResourceVo.class);
        return resourceVos;
    }
}
