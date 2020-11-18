package com.xiahe.pool;

import java.util.*;

/**
 * @description 配置类
 * @author: Yue
 * @create: 2020.01.01 22:35
 **/
public class Configuration {

    // 浏览器标识
    public static Map<String, String> Browser;

    // 加载数据
    static {
        Browser = new HashMap<>();
        try {
            List<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(Configuration.class.getResourceAsStream("/tools/browser.txt"));
            for (String line; scanner.hasNext() && (line = scanner.nextLine()) != null; ) {
                list.add(line);
            }
            scanner.close();

            for (int i = 0; i < list.size(); i += 2) {
                Browser.put(list.get(i), list.get(i + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载配置失败");
        }
    }

    /**
     * 全局代理地址
     */
    public static final String PROXY_URL = "http://zhaoqing.date:";

    /**
     *     TODO 全局配置文件加载  从和Jar包所在路径相同的位置在启动时加载配置文件
     *     如果配置文件加载失败则直接使用默认的配置
     */


}
