package com.pangzixiang.springtokenframework.model;

import org.springframework.http.HttpStatus;

public class ResponseUtil {

    public static Response success(String msg, Object data) {
        Response response = new Response();
        response.setCode(HttpStatus.OK.value());
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static Response success() {
        return success(null, null);
    }

    public static Response success(Object data) {
        return success(null, data);
    }

    public static Response success(String msg) {
        return success(msg,null);
    }

    public static Response error(Integer code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static Response error(Integer code) {
        return error(code,null);
    }
}
