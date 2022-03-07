package com.itheima.shopuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.shopcommon.entitys.user.ShopUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<ShopUserEntity> {
}
