package com.itheima.sys.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.sys.auth.entitys.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    /**
     * 通过账户id查询该账户的权限
     * @param accountId
     * @return
     */
    List<SysRoleEntity> allRoleByAccountId(@Param("accountId") Long accountId);
}
