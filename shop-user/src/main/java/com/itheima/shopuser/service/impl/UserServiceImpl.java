package com.itheima.shopuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.shopcommon.entitys.product.ShopProductEntity;
import com.itheima.shopcommon.entitys.user.ShopUserEntity;
import com.itheima.shopcommon.enums.BigDecimalCompareToEnums;
import com.itheima.shopcommon.exceptions.UserException;
import com.itheima.shopuser.mapper.UserMapper;
import com.itheima.shopuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author 10445
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, ShopUserEntity> implements UserService {
    /**
     * 查询用户信息
     *
     * @param id 用户id
     * @return ShopUserEntity
     */
    @Override
    public ShopUserEntity userOne(Long id) {
        return this.getById(id);
    }

    /**
     * 新增用户
     *
     * @param userEntity
     * @return
     */
    @Override
    public Boolean addUser(ShopUserEntity userEntity) {
        this.save(userEntity);
        return Boolean.TRUE;
    }

    /**
     * 账户服务
     * 扣除|增加余额
     *
     * @param id     用户id
     * @param amount 扣除的金额
     * @return
     */
    @Override
    @Transactional(rollbackFor = UserException.class)
    public Boolean debit(Long id, BigDecimal amount) throws UserException {
        ShopUserEntity userEntity = this.userOne(id);
        BigDecimal balance = userEntity.getUserBalance();
        if (balance.compareTo(amount) == BigDecimalCompareToEnums.LESS.getCode()) {
            //小于
            throw new UserException("用户余额不足");
        }
        BigDecimal newBalance = balance.subtract(amount);
        LambdaUpdateWrapper<ShopUserEntity> updateWrapper = Wrappers.<ShopUserEntity>lambdaUpdate()
                .set(ShopUserEntity::getUserBalance,newBalance).eq(ShopUserEntity::getUserId,id);
        this.update(updateWrapper);
        return Boolean.TRUE;
    }
}
