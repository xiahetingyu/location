package com.xiahe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description 项目启动器
 * @author: Yue
 * @create: 2019.12.31 00:00
 **/
@EnableScheduling
@SpringBootApplication
public class ProxyPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyPoolApplication.class, args);
    }

}
