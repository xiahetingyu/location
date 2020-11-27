package com.xiahe.util;

import com.xiahe.exception.UtilException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * @description 资源位置定位工具
 * @author: Yue
 * @create: 2020.11.28 00:01
 **/
public class ResourcesLocationUtil {

    /**
     * 资源文件获取工具
     *
     * @param resources 资源文件名称
     * @return 资源文件绝对路径
     */
    public static File getResources(String resources) {
        //资源文件夹名称
        String resourcesPath = "resources";
        File currentPath = getCurrentPath();

        //Jar环境就直接返回相对文件夹
        if (isRunningWithJar()) {
            return new File(new File(currentPath, resourcesPath), resources);
        }

        //IDEA环境先检测Classpath的resources文件夹是否存在文件
        currentPath = currentPath.getParentFile();
        File classpathFile = new File(new File(new File(currentPath, "src/main"), resourcesPath), resources);
        return classpathFile.exists() ? classpathFile : new File(new File(currentPath, resourcesPath), resources);
    }

    /**
     * 判断运行环境是否为Jar包
     *
     * @return true Jar  false Idea
     */
    private static boolean isRunningWithJar() {
        URL resource = ResourcesLocationUtil.class.getResource("/");
        return Objects.equals("jar", resource.getProtocol());
    }

    /**
     * 获取当前程序运行的相对文件路径
     *
     * @return 当前所在的相对路径
     */
    private static File getCurrentPath() {
        try {
            return new File(ResourceUtils.extractArchiveURL(ResourcesLocationUtil.class.getResource("/")).getPath()).getParentFile();
        } catch (MalformedURLException e) {
            throw new UtilException(e);
        }
    }

}
