package com.itheima.shopuser.controller;

import com.itheima.shopcommon.dto.DebitBalanceDto;
import com.itheima.shopcommon.dto.UserDto;
import com.itheima.shopcommon.entitys.user.ShopUserEntity;
import com.itheima.shopcommon.exceptions.UserException;
import com.itheima.shopcommon.untils.RegexpClass;
import com.itheima.shopcommon.vo.UserVo;
import com.itheima.shopuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author 10445
 */
@RestController
@Slf4j
@RequestMapping("/su")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 查询用户详情
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public UserVo userOne(@PathVariable("id") Long id) {
        ShopUserEntity userEntity = userService.userOne(id);
        UserVo userVo = UserVo.builder()
                .userId(userEntity.getUserId())
                .userBalance(userEntity.getUserBalance())
                .userName(userEntity.getUserName())
                .userPhone(userEntity.getUserPhone())
                .build();
        return userVo;
    }
    /**
     * 新增用户
     */
    @PostMapping(value = "/add")
    public Boolean userAdd(@RequestBody(required = true) @Valid UserDto dto){
        ShopUserEntity userEntity = ShopUserEntity.builder()
                .userName(dto.getUserName())
                .userPhone(dto.getUserPhone())
                .build();
        userService.addUser(userEntity);
        return Boolean.TRUE;
    }
    /**
     * 扣除用户余额
     */
    @PostMapping(value = "/debit")
    public Boolean debit(@RequestBody(required = true) DebitBalanceDto debitBalanceDto ) throws UserException {
        userService.debit(Long.parseLong(debitBalanceDto.getUserId()),new BigDecimal(debitBalanceDto.getBalance()));
        return Boolean.TRUE;
    }
}
