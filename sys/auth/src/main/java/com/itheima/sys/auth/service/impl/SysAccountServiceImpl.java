package com.itheima.sys.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.sys.auth.entitys.SysAccountEntity;
import com.itheima.sys.auth.mapper.SysAccountMapper;
import com.itheima.sys.auth.service.SysAccountService;
import com.itheima.sys.coredata.dto.request.SysAccountReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 登入账户服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements SysAccountService {

    /**
     * 创建账户
     *
     * @param dto 登入账号信息
     * @return
     */
    @Override
    public Boolean createAccount(SysAccountReqDto dto) {
        SysAccountEntity sysAccountEntity = new SysAccountEntity();
        BeanUtils.copyProperties(dto, sysAccountEntity, SysAccountEntity.class);
        this.save(sysAccountEntity);
        return Boolean.TRUE;
    }
}
