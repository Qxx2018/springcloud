package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysMenuEntity;
import com.itheima.sys.auth.mapper.SysMenuMapper;
import com.itheima.sys.auth.service.SysMenuService;
import com.itheima.sys.coredata.dto.request.SysMenuReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统菜单服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    /**
     * 创建菜单
     *
     * @param dto 创建菜单dto
     * @return
     */
    @Override
    public Boolean createMenu(SysMenuReqDto dto) {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        BeanUtils.copyProperties(dto, sysMenuEntity, SysMenuEntity.class);
        this.save(sysMenuEntity);
        return Boolean.TRUE;

    }
}
