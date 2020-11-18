package com.xiahe.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description 池
 * @author: Yue
 * @create: 2020.01.01 22:36
 **/
public class Pool {

    /**
     * 全局缓冲线程池
     */
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

}
