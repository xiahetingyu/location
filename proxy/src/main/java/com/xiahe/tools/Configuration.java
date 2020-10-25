package com.xiahe.tools;

import java.util.*;

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

}
