package com.gtop.reda.test;

import lombok.Data;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 17:26
 */
@Data
public class Result<T> {

    private String code;

    private T data;

    public Result(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {

        return new Result<>("OK", data);
    }

}
