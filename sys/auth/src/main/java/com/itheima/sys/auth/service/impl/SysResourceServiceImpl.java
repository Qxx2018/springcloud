package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.auth.mapper.SysResourceMapper;
import com.itheima.sys.auth.service.SysResourceService;
import com.itheima.sys.coredata.dto.request.SysResourceReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统权限资源服务
 * @author 10445
 */
@Service
@Slf4j
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResourceEntity> implements SysResourceService {

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
}
