package com.example.jpademo.config.exception;

/**
 * @ClassName: MessageException
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/12 14:10
 * @Version: 1.0
 */
public class MessageException extends RuntimeException {
    private int code = 999;

    public MessageException(String message) {
        super(message);
    }

    public MessageException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
