package com.itheima.sys.coredata.dto.base.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * jwt token 数据格式
 * @author 10445
 */
@Data
public class JwtTokenDo implements Serializable {

    private static final long serialVersionUID = -1823225379517829643L;
    /**
     * 头
     */
    private Map<String, Object> header;
    /**
     * 负载PayLoadDo
     */
    private PayLoadDo payLoadDo;

    /**
     * 过期时间（多少天过期）
     */
    private Integer time;

    /**
     * 加密密钥
     */
    private String tokenSecret;
}
