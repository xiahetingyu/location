package com.xiahe;

import com.xiahe.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @description 项目启动器
 * @author: Yue
 * @create: 2019.12.31 00:00
 **/
@PropertySource(factory = ApplicationConfiguration.class, value = "application.yml")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
