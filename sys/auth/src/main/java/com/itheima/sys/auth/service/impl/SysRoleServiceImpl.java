package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysRoleEntity;
import com.itheima.sys.auth.mapper.SysRoleMapper;
import com.itheima.sys.auth.service.SysRoleService;
import com.itheima.sys.corebase.utils.BeanCopyUtils;
import com.itheima.sys.coredata.dto.request.SysRoleReqDto;
import com.itheima.sys.coredata.dto.response.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
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

    /**
     * 通过账户id查询该账户的角色
     *
     * @param accountId 账户id
     * @return
     */
    @Override
    public List<RoleVo> allRoleByAccountId(Long accountId) {
        List<SysRoleEntity> sysRoleEntities = sysRoleMapper.allRoleByAccountId(accountId);
        return BeanCopyUtils.convertList(sysRoleEntities, RoleVo.class);
    }
}
