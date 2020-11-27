package com.xiahe.config;

import com.xiahe.exception.ConfigException;
import com.xiahe.util.ResourcesLocationUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * @description 静态资源加载器
 * @author: Yue
 * @create: 2020.11.22 19:07
 **/
@Configuration
public class LocationStaticConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        StringBuilder stringBuilder = new StringBuilder("file:");
        File resources = ResourcesLocationUtil.getResources("static");

        //如果是文件则转向文件内容所指向的位置
        if (resources.isFile()) {
            try {
                resources = new File(FileUtils.readFileToString(resources));
            } catch (Exception e) {
                throw new ConfigException(e);
            }
        }

        //设置被转换的目标位置
        stringBuilder.append(resources.getAbsolutePath());
        stringBuilder.append(File.separator);
        registry.addResourceHandler("/**").addResourceLocations(stringBuilder.toString());
    }

}
