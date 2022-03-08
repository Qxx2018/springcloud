package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysRoleEntity;
import com.itheima.sys.auth.mapper.SysRoleMapper;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.coredata.dto.request.SysRoleReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统角色服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    /**
     * 创建系统角色
     *
     * @param dto 角色信息
     * @return
     */
    @Override
    public Boolean createRole(SysRoleReqDto dto) {
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(dto, sysRoleEntity, SysRoleEntity.class);
        this.save(sysRoleEntity);
        return Boolean.TRUE;
    }
}
