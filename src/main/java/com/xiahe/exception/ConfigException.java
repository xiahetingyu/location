package com.xiahe.exception;

/**
 * @description 配置类异常
 * @author: Yue
 * @create: 2020.11.28 02:26
 **/
public class ConfigException extends RuntimeException {

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
