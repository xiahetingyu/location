package com.xiahe.controller;

import com.xiahe.service.ParameterService;
import com.xiahe.util.ResourcesLocationUtil;
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
    private ParameterService configService;

    @RequestMapping("select")
    public Object select(String key) {
        return configService.getParameterValue(key);
    }

    @RequestMapping("save")
    public Object save(String key, String value) {
        configService.setParameterValue(key, value);
        return "ok";
    }

    @RequestMapping("getPath")
    public Object getPath(String resources) {
        return ResourcesLocationUtil.getResources(resources);
    }

}
