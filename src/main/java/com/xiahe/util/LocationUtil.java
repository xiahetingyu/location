package com.xiahe.util;

import com.xiahe.config.LoadYmlConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * @description 常用工具类
 * @author: Yue
 * @create: 2020.11.23 01:10
 **/
public class LocationUtil {

    /**
     * 是否是调试模式
     */
    private static boolean isDebug;

    /**
     * 获取配置文件所在位置
     *
     * @param resources 配置文件
     * @return 配置文件所在位置
     * @throws MalformedURLException 路径格式异常
     */
    public static File getResources(String resources) throws MalformedURLException {
        File currentPath = new File(ResourceUtils.extractArchiveURL(LoadYmlConfiguration.class.getResource("/")).getPath());
        currentPath = isRunningWithJar() ? currentPath.getParentFile() : currentPath.getParentFile().getParentFile();
        return new File(new File(currentPath, "resources"), resources);
    }


    /**
     * 判断运行环境是否为Jar包
     *
     * @return true Jar  false Idea
     */
    public static boolean isRunningWithJar() {
        URL resource = LoadYmlConfiguration.class.getResource("/");
        return Objects.equals("jar", resource.getProtocol());
    }

    /**
     * 打印异常
     *
     * @param e 异常
     */
    public static void printException(Exception e) {
        if (isDebug) {
            e.printStackTrace();
        }
    }

}
