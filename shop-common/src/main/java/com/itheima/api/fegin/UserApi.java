package com.itheima.api.fegin;

import com.itheima.shopcommon.dto.DebitBalanceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 服务间调用：用户接口
 * @author 10445
 */
@Component
@FeignClient(name = "shop-user-service", url = "http://192.168.237.1:8183")
public interface UserApi {
    /**
     * 扣除用户余额
     * @param debitBalanceDto
     */
    @PostMapping(value = "/su/debit")
    Boolean debit(@RequestBody(required = true) DebitBalanceDto debitBalanceDto);
}
