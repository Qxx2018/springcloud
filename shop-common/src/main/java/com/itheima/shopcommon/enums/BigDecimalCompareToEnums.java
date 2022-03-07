package com.itheima.shopcommon.enums;

import lombok.Data;

/**
 * BigDecimal比较大小枚举
 * = -1 小于
 * = 0 等于
 * = 1 大于
 * < 1 小于等于
 * > -1 大于等于
 * @author 10445
 */
public enum  BigDecimalCompareToEnums {

    LESS(-1,"小于"),
    EQUAL(0,"等于"),
    GREATER(1,"大于")
    ;


    private Integer code;
    private String msg;

    BigDecimalCompareToEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }
    public String getMsg() {
        return this.msg;
    }
}
