package com.xiahe.config;

import com.xiahe.util.ResourcesLocationUtil;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * @description 应用资源加载器
 * @author: Yue
 * @create: 2020.11.22 22:16
 **/
public class ApplicationConfiguration implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
        //获取配置文件名
        name = Optional.ofNullable(name).orElse(encodedResource.getResource().getFilename());

        //加载配置文件
        File resources = ResourcesLocationUtil.getResources(name);
        Properties properties = new Properties();
        if (resources.isFile()) {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new FileSystemResource(resources.getPath()));
            factory.afterPropertiesSet();
            properties = Optional.ofNullable(factory.getObject()).orElse(properties);
        }

        //更改密码配置
        updateDatabasePassword(properties);

        //返回加载后的配置内容
        return new PropertiesPropertySource(name, properties);
    }

    /**
     * 更改数据库密码配置
     *
     * @param properties 配置文件内容
     */
    private void updateDatabasePassword(Properties properties) {
        //如果包含用户名且不包含密码则自动获取环境变量密码
        String usernameKey = "spring.datasource.username";
        String passwordKey = "spring.datasource.password";
        if (properties.containsKey(usernameKey) && !properties.containsKey(passwordKey)) {
            properties.setProperty(passwordKey, System.getenv("DatabasePassword"));
        }
    }

}
