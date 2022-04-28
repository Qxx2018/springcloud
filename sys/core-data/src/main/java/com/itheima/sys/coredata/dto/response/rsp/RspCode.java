package com.itheima.sys.coredata.dto.response.rsp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 10445
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum RspCode {

    SUCCESS(0, "请求成功"),
    FAIL(-1, "业务处理失败"),
    /**
     * 系统级代码
     */

    /**
     *  业务代码
     */
    BUSINESS_LOGIN_DISABLED(10001,"登录失效"),
    BUSINESS_LOGIN_SUCCESS(10002,"登录成功"),
    BUSINESS_LOGIN_ERROR(10003,"登录失败"),
    BUSINESS_INSUFFICIENT_PRIVILEGES(10101,"权限不足"),
    ;

    private Integer code;
    private String msg;

    /**
     * 通过code返回对应的错误信息
     *
     * @param code
     * @return
     */
    public static String getMsg(Integer code) {
        for (RspCode apiCodeEnum : RspCode.values()) {
            if (code.equals(apiCodeEnum.getCode())) {
                return apiCodeEnum.getMsg();
            }
        }
        return FAIL.getMsg();
    }
}
