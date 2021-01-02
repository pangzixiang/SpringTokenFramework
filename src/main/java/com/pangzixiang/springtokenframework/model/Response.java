package com.pangzixiang.springtokenframework.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

    private Integer code;
    private String msg;
    private T data;
}
