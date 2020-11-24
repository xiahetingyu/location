package com.xiahe.controller;

import com.xiahe.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.23 21:44
 **/
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("select")
    public Object select(String key) {
        return configService.getConfigValue(key);
    }

    @RequestMapping("save")
    public Object save(String key, String value) {
        configService.setConfigValue(key, value);
        return "ok";
    }

}
