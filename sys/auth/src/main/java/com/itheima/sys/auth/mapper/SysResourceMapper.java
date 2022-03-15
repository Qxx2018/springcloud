package com.itheima.sys.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.sys.auth.entitys.SysResourceEntity;
import com.itheima.sys.coredata.dto.response.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysResourceMapper extends BaseMapper<SysResourceEntity> {


    /**
     * 根据角色ids获取角色下所有的资源权限
     * @param ids
     * @return
     */
    List<SysResourceEntity> allResourceByRoleIds( @Param("ids") List<Long> ids);
}
