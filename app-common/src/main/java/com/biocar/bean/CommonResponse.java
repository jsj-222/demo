package com.biocar.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DeSen Xu
 * @date 2021-10-29 18:53
 */
public class CommonResponse<T> {

    private int code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponse(int code) {
        this(code, null);
        this.code = code;
    }

    public CommonResponse(int code, T data) {
        this(code, "success", null);
        this.code = code;
        this.data = data;
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
