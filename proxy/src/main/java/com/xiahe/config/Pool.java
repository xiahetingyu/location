package com.xiahe.config;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class Pool {

    // 核心线程池
    public static Executor Executors = java.util.concurrent.Executors.newCachedThreadPool();

    // 待验证队列
    public static BlockingQueue<JSONObject> Cache = new LinkedBlockingQueue<>(32);

    // 待使用队列
    public static BlockingQueue<JSONObject> Pool = new LinkedBlockingQueue<>(64);

}
