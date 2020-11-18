package com.xiahe.core;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.config.Pool;
import com.xiahe.tools.Configuration;
import com.xiahe.tools.NetworkTools;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Timestamp;

//可用性验证器 
public class Verification implements Runnable {
    // 验证地址
    private static String Ver = "https://account.chsi.com.cn/account/password!rtvlgname";

    // 验证代理
    public static boolean verification(JSONObject proxy) {
        proxy.put("proxy", new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(proxy.getString("ip"), proxy.getIntValue("port"))));
//        for (int i = 0; i < 2; i++) {
        try {
            return NetworkTools.networkForString(Configuration.Browser, (Proxy) proxy.get("proxy"), Ver, "GET") != null;
        } catch (Exception exception) {

        }
//        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                JSONObject proxy = Pool.Cache.take();
                boolean verification = verification(proxy);
                if (verification) {
                    proxy.put("proxy", proxy.getString("ip") + ":" + proxy.getIntValue("port"));
                    proxy.put("time", new Timestamp(System.currentTimeMillis()));
                    System.out.println("Verification:" + proxy);
                    Pool.Pool.put(proxy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
