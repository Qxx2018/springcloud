package com.itheima.sys.coredata.dto.response.rsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public class Rsp<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6088471606989866507L;

    private Integer code;

    private String msg;

    private Long timestamp;

    private T data;

    private Exception exception;

    /**
     * 系统定义的错误返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error() {
        return error(RspCode.FAIL.getCode(), RspCode.FAIL.getMsg());
    }

    /**
     * @param code 传入对应的错误码 自动返回对应错误消息
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(Integer code) {
        return error(code, RspCode.getMsg(code));
    }

    /**
     * 自定义返回错误结果 默认错误码为1 业务处理失败
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(String message) {
        return error(RspCode.FAIL.getCode(), message);
    }

    /**
     * @param rspCode 传入对应的错误码枚举 自动返回对应错误消息
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(RspCode rspCode) {
        return error(rspCode.getCode(), rspCode.getMsg());
    }

    /**
     * @param rspCode 传入对应的错误码枚举 自动返回对应错误消息
     * @param data
     * @param e
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(RspCode rspCode, T data, Exception e) {
        return error(rspCode.getCode(), rspCode.getMsg(), data, e);
    }

    /**
     * 使用系统默认错误码 传入错误返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(T data) {
        return new Rsp<T>().data(data).code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg());
    }

    /**
     * 全部自定义消息 与错误码
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(Integer code, String message) {
        return new Rsp<T>().code(code).msg(message);
    }

    /**
     * 传入对应错误吗和相关的错误返回结果
     *
     * @param code
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(Integer code, T data) {
        return new Rsp<T>().code(code).data(data);
    }

    /**
     * 传入对应错误信息和相关的错误返回结果
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(String message, T data) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(message).data(data);
    }

    /**
     * 全部自定义
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(Integer code, String message, T data) {
        return new Rsp<T>().data(data).code(code).msg(message);
    }

    /**
     * 全部自定义
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> error(Integer code, String message, T data, Exception e) {
        return new Rsp<T>().data(data).code(code).msg(message).exception(e);
    }

    public static <T> Rsp<T> ok() {
        return new Rsp<T>().code(RspCode.SUCCESS.getCode()).msg(RspCode.SUCCESS.getMsg());
    }

    /**
     * 请求成功 默认code为0 掺入对应的返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> ok(T data) {
        return new Rsp<T>().data(data).code(RspCode.SUCCESS.getCode()).msg(RspCode.SUCCESS.getMsg());
    }

    /**
     * 传入对应的成功枚举 和数据
     * @param rspCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> ok(RspCode rspCode, T data) {
        return new Rsp<T>().code(rspCode.getCode()).msg(rspCode.getMsg()).data(data);
    }

    /**
     * 自定义ok
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Rsp<T> ok(Integer code, String message, T data) {
        return new Rsp<T>().data(data).code(code).msg(message);
    }

    public Rsp() {
        this.timestamp = System.currentTimeMillis();
    }

    public Rsp<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Rsp<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Rsp<T> data(T data) {
        this.data = data;
        return this;
    }

    public Rsp<T> exception(Exception e) {
        this.exception = e;
        return this;
    }

    public boolean isOK() {
        if (null != this.code && this.code.equals(RspCode.SUCCESS.getCode())) {
            return true;
        }
        return false;
    }
}
