package com.xiahe.service;

/**
 * @description 配置管理服务
 * @author: Yue
 * @create: 2020.11.22 16:59
 **/
public interface ConfigService {

    /**
     * 获取配置项
     *
     * @param key 配置键
     * @return 配置值
     */
    String getConfigValue(String key);

    /**
     * 设置配置项
     *
     * @param key   配置键
     * @param value 配置值
     */
    void setConfigValue(String key, String value);

}
