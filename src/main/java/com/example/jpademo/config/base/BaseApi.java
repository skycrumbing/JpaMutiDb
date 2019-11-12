package com.example.jpademo.config.base;

/**
 * @ClassName: baseApi
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/12 14:12
 * @Version: 1.0
 */
public class BaseApi {
    public BaseApi() {
    }

    public Result success(Object data) {
        return new Result(0, data);
    }

    public Result success(String message, Object data) {
        return this.success(0, message, data);
    }

    public Result success(int code, Object data) {
        return new Result(code, data);
    }

    public Result success(int code, String message, Object data) {
        return new Result(code, message, data);
    }
}
