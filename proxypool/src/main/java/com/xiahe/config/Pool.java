package com.xiahe.config;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class Pool implements Runnable {

    // 核心线程池
    public static Executor Executors = java.util.concurrent.Executors.newCachedThreadPool();

    // 待验证队列
    public static BlockingQueue<JSONObject> Cache = new LinkedBlockingQueue<>(32);

    // 待使用队列
    public static BlockingQueue<JSONObject> Pool = new LinkedBlockingQueue<>(64);


    //----------------------------心跳-------------------------------------
    static {//心跳状态
        Executors.execute(new Pool());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                JSONObject size = new JSONObject();
                size.put("Date", new Date());
                size.put("Cache", Cache.size());
                size.put("Pool", Pool.size());
                System.out.println("Pool:" + size);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
