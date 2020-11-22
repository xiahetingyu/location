package com.xiahe.config;

import com.xiahe.util.RSADealUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * @description 配置文件加载器
 * @author: Yue
 * @create: 2020.11.22 22:16
 **/
public class LoadYmlConfiguration implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
        //获取配置文件名
        name = Optional.ofNullable(name).orElse(encodedResource.getResource().getFilename());
        Properties properties = new Properties();

        try {
            //加载配置文件
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(getYmlLocation(name));
            factory.afterPropertiesSet();

            //解密配置文件
            properties = decryptYmlContent(factory.getObject());
        } catch (Exception e) {
            System.err.println("createPropertySource error. " + e.getMessage());
        }

        return new PropertiesPropertySource(name, properties);
    }

    /**
     * 获取YML配置文件路径
     *
     * @param ymlName 文件名
     * @return 文件绝对路径
     * @throws MalformedURLException 路径格式异常
     */
    private FileSystemResource getYmlLocation(String ymlName) throws MalformedURLException {
        File currentPath = new File(ResourceUtils.extractArchiveURL(this.getClass().getResource("/")).getPath());
        currentPath = isRunningWithJar() ? currentPath.getParentFile() : new File(currentPath.getParentFile().getParentFile(), "/src/main/");
        File ymlFile = new File(new File(currentPath, "resources"), ymlName);
        return new FileSystemResource(ymlFile.getPath());
    }

    /**
     * 解密YML配置文件
     *
     * @param properties 原始配置文件
     * @return 解密后配置文件
     */
    private Properties decryptYmlContent(Properties properties) {
        //返回结果
        properties = Optional.ofNullable(properties).orElse(new Properties());

        try {
            //尝试解密
            String key = "spring.datasource.password";
            String publicKey = IOUtils.toString(this.getClass().getResource("/key/pri.pem"));
            properties.setProperty(key, RSADealUtil.privateDecrypt(publicKey, properties.getProperty(key)));
        } catch (Exception e) {
            System.err.println("decryptYmlContent error. " + e.getMessage());
        }

        return properties;
    }

    /**
     * 判断运行环境是否为Jar包
     *
     * @return true Jar  false Idea
     */
    public static boolean isRunningWithJar() {
        URL resource = LoadYmlConfiguration.class.getResource("/");
        System.out.println(resource);
        return Objects.equals("jar", resource.getProtocol());
    }

}
