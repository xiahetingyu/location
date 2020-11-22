package com.xiahe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.22 19:07
 **/
@Configuration
public class LocationConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("AAAAAAAAAAAA" + getResourcesPath());
        registry.addResourceHandler("/**").addResourceLocations("file:C:\\Development\\GitHub\\location\\resources\\static\\");
    }

    public static File getResourcesPath() {
        //当前路径
        File currentPath = new File(LocationConfiguration.class.getResource("/").getPath());

        //资源路径
        return new File(currentPath.getParent(), "resources");
    }

}
