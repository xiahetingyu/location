package com.xiahe.service;

import com.xiahe.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 配置管理服务实现
 * @author: Yue
 * @create: 2020.11.22 16:59
 **/
@Component
public class ConfigServiceImpl implements ConfigService {

    /**
     * 配置缓存
     */
    private Map<String, String> configCache = new ConcurrentHashMap<>();

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public String getConfigValue(String key) {
        return null;
    }

    @Override
    public void setConfigValue(String key, String value) {

    }

}
