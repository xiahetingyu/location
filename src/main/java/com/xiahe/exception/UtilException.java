package com.xiahe.exception;

/**
 * @description 工具类异常
 * @author: Yue
 * @create: 2020.11.22 07:17
 **/
public class UtilException extends RuntimeException {

    public UtilException() {
        super();
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

}
