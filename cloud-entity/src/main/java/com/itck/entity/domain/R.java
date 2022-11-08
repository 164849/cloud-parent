package com.itck.entity.domain;

import lombok.Data;

import javax.xml.ws.Response;

@Data
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 响应成功的方法
    public static <T> R<T> ok(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    // 响应成功的方法
    public static <T> R<T> ok(T data) {
        return ok(200, "成功", data);
    }

    // 响应成功的方法
    public static <T> R<T> ok() {
        return ok(null);
    }

    // 响应失败的方法
    public static <T> R<T> fail(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    // 响应失败的方法
    public static <T> R<T> fail(T data) {
        return fail(-1, "失败", data);
    }

    // 响应失败的方法
    public static <T> R<T> fail() {
        return fail(null);
    }

}