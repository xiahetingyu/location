package com.xiahe.service;

/**
 * @description 参数管理服务
 * @author: Yue
 * @create: 2020.11.22 16:59
 **/
public interface ParameterService {

    /**
     * 获取参数项
     *
     * @param key 参数键
     * @return 参数值
     */
    String getParameterValue(String key);

    /**
     * 设置参数项
     *
     * @param key   参数键
     * @param value 参数值
     */
    void setParameterValue(String key, String value);

}
