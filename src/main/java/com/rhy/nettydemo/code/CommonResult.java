package com.rhy.nettydemo.code;


import java.io.Serializable;

/**
 * @program: cloud-service
 * @description: 通用返回类
 * @author: Rhy
 * @create_time: 2020-06-28 09:40
 * @modifier：Rhy
 * @modification_time：2020-06-28 09:40
 **/
public class CommonResult implements Serializable {

    private long code;

    private String message;


    public CommonResult() {
    }

    public CommonResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
