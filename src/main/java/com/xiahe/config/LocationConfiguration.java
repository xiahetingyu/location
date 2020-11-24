package com.xiahe.config;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.exception.UtilException;
import com.xiahe.util.LocationUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * @description 静态资源转换配置
 * @author: Yue
 * @create: 2020.11.22 19:07
 **/
@Configuration
public class LocationConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        StringBuilder stringBuilder = new StringBuilder("file:");

        try {
            //存在location.json就转向location
            String locationConfig = FileUtils.readFileToString(LocationUtil.getResources("location.json"));
            JSONObject locationJSONObject = JSONObject.parseObject(locationConfig);
            String location = locationJSONObject.getString("location");
            stringBuilder.append(location);
        } catch (Exception e) {
            LocationUtil.printException(new UtilException("addResourceHandlers error.", e));
        }

        try {
            //设置被转换的目标位置
            stringBuilder.append(stringBuilder.length() > 5 ? "" : LocationUtil.getResources("static").getPath());
            stringBuilder.append(File.separator);
            registry.addResourceHandler("/**").addResourceLocations(stringBuilder.toString());
        } catch (Exception e) {
            LocationUtil.printException(new UtilException("addResourceHandlers error.", e));
            super.addResourceHandlers(registry);
        }
    }

}
