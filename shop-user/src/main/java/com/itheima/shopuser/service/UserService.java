package com.itheima.shopuser.service;

import com.itheima.shopcommon.entitys.user.ShopUserEntity;
import com.itheima.shopcommon.exceptions.UserException;
import com.itheima.shopcommon.service.BaseService;

import java.math.BigDecimal;

/**
 * 用户服务
 * @author 10445
 */
public interface UserService extends BaseService<ShopUserEntity> {

    /**
     * 查询用户信息
     * @param id 用户id
     * @return ShopUserEntity
     */
    ShopUserEntity userOne(Long id);

    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    Boolean addUser(ShopUserEntity userEntity);
    /**
     * 账户服务
     * 扣除|增加余额
     * @param id 用户id
     * @param amount 扣除的金额
     * @return
     */
    Boolean debit(Long id, BigDecimal amount) throws UserException;

}
