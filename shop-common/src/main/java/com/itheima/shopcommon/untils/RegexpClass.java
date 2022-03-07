package com.itheima.shopcommon.untils;

import java.io.Serializable;

/**
 * 正则工具类
 * @author 10445
 */
public class RegexpClass implements Serializable {
    /**
     * 金额正则
     * 0.00
     * 1
     * 2.03
     */
    public static final  String BALANCE = "(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0(\\.\\d{1,2})?$)";
    /**
     * 自然数正则
     */
    public static final String NUM = "(^[1-9]+\\d*$)|(^0$)";

}
