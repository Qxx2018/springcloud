package com.itheima.shoporder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.shopcommon.dto.PreferentialDto;
import com.itheima.shopcommon.entitys.order.ShopOrderPreferentialEntity;
import com.itheima.shopcommon.exceptions.OrderException;
import com.itheima.shoporder.mapper.ShopOrderPreferentialMapper;
import com.itheima.shoporder.service.ShopOrderPreferentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单优惠服务实现类
 * @author 10445
 */
@Service
@Slf4j
public class ShopOrderPreferentialServiceImpl extends ServiceImpl<ShopOrderPreferentialMapper, ShopOrderPreferentialEntity> implements ShopOrderPreferentialService {
    /**
     * 创建订单优惠记录
     *
     * @param dto 订单优惠传入
     * @return
     */
    @Override
    @Transactional(rollbackFor = OrderException.class)
    public ShopOrderPreferentialEntity createPreferentialRecord(ShopOrderPreferentialEntity dto) throws OrderException {
        super.baseMapper.insert(dto);
        String code = "COUPON-0001";
        if (!code.equals(dto.getTialCode())) {
            throw new OrderException("优惠编码不对");
        }
        return dto;
    }
}
