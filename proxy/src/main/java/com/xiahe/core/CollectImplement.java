package com.xiahe.core;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.config.Pool;

import java.util.List;

public class CollectImplement implements Collect {

    @Override
    public void collect() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 从网页中匹配代理IP
    public List<JSONObject> pattern(String content) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //放入待验证队列
    public int put(List<JSONObject> proxies) {
        for (JSONObject proxy : proxies) {
            try {
                Pool.Cache.put(proxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return proxies.size();
    }

    @Override
    public void next() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        collect();
        next();
    }

}
