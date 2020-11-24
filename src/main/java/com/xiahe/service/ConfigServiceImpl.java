package com.xiahe.service;

import com.xiahe.entity.ConfigEntity;
import com.xiahe.exception.UtilException;
import com.xiahe.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description 配置管理服务实现
 * @author: Yue
 * @create: 2020.11.22 16:59
 **/
@Component
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public String getConfigValue(String key) {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setMyKey(key);
        Optional<ConfigEntity> findOne = configRepository.findOne(Example.of(configEntity));
        configEntity = findOne.orElseThrow(() -> new UtilException(key + " not found."));
        return configEntity.getMyValue();
    }

    @Override
    public void setConfigValue(String key, String value) {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setMyKey(key);
        configEntity.setMyValue(value);
        configRepository.save(configEntity);
    }

}
